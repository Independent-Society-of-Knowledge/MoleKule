package org.isk.molekule.gen.lammps

import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeSingleton
import org.isk.molekule.gen.Environment
import org.slf4j.LoggerFactory
import java.io.StringReader
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.io.path.pathString
import kotlin.streams.asSequence

class LAMMPS(
    private val baseDirectory: Path = Paths.get("output")
) {

    private var runtimeDirectory: Path? = null

    private var script: String = "# generated with molekule-gen"
    private val velocityEngine = VelocityEngine().also { it.init() }
    val ctx = VelocityContext()
    fun addScriptFile(path: Path): LAMMPS = addCommand(Files.readString(path))
    fun addScriptFile(path: String): LAMMPS = addScriptFile(Path.of(path))
    fun addCommand(cmd: String): LAMMPS {
        script += "\n${cmd}"
        return this
    }

    fun compile(): String {
        val reader = StringReader(script)
        val rs = RuntimeSingleton.getRuntimeServices()
        val template = Template()
        val node = rs.parse(reader, template)
        template.data = node
        template.setRuntimeServices(rs)
        template.initDocument()

        val writer = StringWriter()
        template.merge(ctx, writer)

        return writer.toString()
    }

    fun compileToFile(path: Path): LAMMPS {
        Files.writeString(path, compile())
        return this
    }

    fun compileToFile(path: String): LAMMPS = compileToFile(Path.of(path))

    fun createRuntimeDir(): Path {
        if(runtimeDirectory != null) return runtimeDirectory!!
        baseDirectory.toFile().mkdirs()
        val lastRuntime = Files.list(baseDirectory)
            .asSequence()
            .map { it.pathString }
            .filter { it.contains("molekul-runtime") }
            .map { it.split("-") }
            .map { it.last() }
            .map { it.toLong() }
            .sortedDescending()
            .firstOrNull() ?: 0

        runtimeDirectory = baseDirectory.resolve("molekul-runtime-${lastRuntime + 1}")
        Files.createDirectories(runtimeDirectory!!)
        return runtimeDirectory!!
    }

    fun createEnvironment(): Path {
        val runtimeDir = createRuntimeDir()
        val inputFile = runtimeDir.resolve("input.in")
        compileToFile(inputFile)
        LoggerFactory.getLogger(javaClass).info("running script:\n${Files.readString(inputFile)}")
        return inputFile
    }

    fun includeEnvironment(
        environment: Environment,
        name: String = "data.in",
        atomStyle: AtomStyle = AtomStyle.FULL,
        enclosingBoxOffset: Double = 0.0
    ): LAMMPS {
        environment.toLammpsInputFile(
            createRuntimeDir().resolve(name).absolutePathString(),
            atomStyle,
            enclosingBoxOffset
        )
        return this
    }

    fun includeFile(path: Path) {
        val runtimeDir = createRuntimeDir()
        Files.copy(path, runtimeDir.resolve(path))
    }
    fun includeFile(path: String) = includeFile(Path.of(path))
    fun runSerial(): Int {
        val inputFile = createEnvironment()
        return ProcessBuilder()
            .command("lmp", "-in", inputFile.absolutePathString())
            .directory(inputFile.parent.toFile())
            .inheritIO()
            .start()
            .waitFor()

    }

    fun runMPI(np: Int = 4): Int {
        val inputFile = createEnvironment()
        return ProcessBuilder()
            .command("mpirun", "-np", np.toString(), "lmp", "-in", inputFile.absolutePathString())
            .directory(inputFile.parent.toFile())
            .inheritIO()
            .start()
            .waitFor()

    }

}

operator fun VelocityContext.get(key: String) = this.get(key)
operator fun VelocityContext.set(key: String, value: Any) {
    this.put(key, value)
}