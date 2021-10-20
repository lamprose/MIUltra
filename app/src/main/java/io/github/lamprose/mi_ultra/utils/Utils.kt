package io.github.lamprose.mi_ultra.utils

import java.io.DataOutputStream
import kotlin.system.exitProcess

import android.content.ComponentName
import android.content.Context

import android.content.Intent
import android.net.Uri


object Utils {
    fun isModuleActive() = false

    fun restartApp(pkg: String, isExit: Boolean = true) {
        execProcess("pkill -f $pkg", true, isExit)
    }

    fun clearAppUserData(pkg: String) {
        execProcess("pm clear $pkg")
    }

    fun goToAppManger(context: Context, pkg: String) {
        context.startActivity(Intent("android.settings.APPLICATION_DETAILS_SETTINGS").apply {
            component = ComponentName(
                "com.android.settings",
                "com.android.settings.applications.InstalledAppDetails"
            )
            data = Uri.parse("package:$pkg")
        })
    }

    fun execProcess(command: String, needRoot: Boolean = true, isExit: Boolean = true) {
        val process = if (needRoot) {
            val suProcess = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(suProcess.outputStream)
            os.writeBytes(command)
            os.flush()
            os.close()
            suProcess
        } else {
            Runtime.getRuntime().exec(command)
        }
        val exitValue = process.waitFor()
        if (isExit) {
            if (exitValue == 0) {
                exitProcess(0)
            } else {
                throw Exception()
            }
        }

    }
}