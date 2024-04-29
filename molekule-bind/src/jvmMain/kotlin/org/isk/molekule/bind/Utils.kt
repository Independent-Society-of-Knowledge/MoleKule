package org.isk.molekule.bind

import java.io.OutputStream

fun OutputStream.writeLine(string: String): OutputStream =
    this.also {
        it.write("$string\n".encodeToByteArray())
    }

operator fun OutputStream.plus(string: String) = writeLine(string)
