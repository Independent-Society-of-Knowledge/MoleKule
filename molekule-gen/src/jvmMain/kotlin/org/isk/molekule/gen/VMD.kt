package org.isk.molekule.gen

import org.apache.commons.lang3.SystemUtils
import org.isk.molekule.core.Environment
import org.isk.molekule.gen.lammps.toLammpsDumpFile
import java.io.File
import java.io.FileOutputStream


class VMD {
    init {
        val res = if (SystemUtils.IS_OS_UNIX) {
            val check = Runtime.getRuntime().exec("whereis vmd")
            check.waitFor()
            check.inputStream.readAllBytes().decodeToString().trim().replace("vmd:", "").isNotEmpty()
        } else if (SystemUtils.IS_OS_WINDOWS) {
            val check = Runtime.getRuntime().exec("where vmd")
            check.waitFor()
            check.inputStream.readAllBytes().decodeToString().isNotEmpty()
        } else {
            System.err.println("can't determine if VMD exist in your system")
            true
        }
        if (!res)
            throw IllegalStateException("VMD is not in your path")
    }

    private var command = ""
    fun addCommand(c: String): VMD {
        command += c + "\n"
        return this
    }

    private var instance: Process? = null
    private var vmdFile: File? = null
    fun run(): VMD {

        vmdFile = File.createTempFile("molkul-", ".vmd-script")
        FileOutputStream(vmdFile!!).use {
            it.write(command.encodeToByteArray())
        }

        instance = ProcessBuilder()
            .command(
                listOf(
                    "vmd",
                    preCommand,
                    "-e",
                    vmdFile!!.path
                ).filter { it.isNotEmpty() }
            )
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
        return this
    }

    private var preCommand: String = ""
    fun setPreCommand(preCommand: String): VMD {
        this.preCommand = preCommand
        return this

    }

    fun vdm(size: Double = 1.0): VMD {
        addCommand("mol delrep 0 0")
        addCommand("mol color Type")
        addCommand("mol representation VDW $size 15.000000")
        addCommand("mol selection all")
        addCommand("mol material Opaque")
        addCommand("mol modrep 0 0")
        addCommand("mol modstyle 0 0 VDW $size 15.000000")
        return this
    }

    fun dynamicBonds(size: Double = 0.5, cutOff: Double = 1.6): VMD {
        addCommand("mol delrep 0 0")
        addCommand("mol color Type")
        addCommand("mol representation DynamicBonds $cutOff $size 12.000000")
        addCommand("mol selection all")
        addCommand("mol material Opaque")
        addCommand("mol addrep 0")
        return this
    }

    fun waitUntilExit() {
        instance?.waitFor()
        vmdFile?.deleteOnExit()
    }
}

fun Environment.createVMD(rescale: Double = 0.1): VMD {
    return VMD().also {
        val outputFile = File.createTempFile("molkul-", ".lammpstrj")
        toLammpsDumpFile(outputFile.path, rescale = rescale)
        it.setPreCommand(outputFile.path)
        it.vdm()
        outputFile.deleteOnExit()
    }
}
