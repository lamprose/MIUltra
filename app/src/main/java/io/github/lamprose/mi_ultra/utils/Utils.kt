package io.github.lamprose.mi_ultra.utils

import java.io.DataOutputStream
import kotlin.system.exitProcess

object Utils {
    fun isModuleActive() = false

    fun restartApp(pkg: String, isExit: Boolean = true) {
        val suProcess = Runtime.getRuntime().exec("su")
        val os = DataOutputStream(suProcess.outputStream)
        os.writeBytes("pkill -f $pkg")
        os.flush()
        os.close()
        val exitValue = suProcess.waitFor()
        if (exitValue == 0 && isExit) {
            exitProcess(0)
        } else {
            throw Exception()
        }
    }
}