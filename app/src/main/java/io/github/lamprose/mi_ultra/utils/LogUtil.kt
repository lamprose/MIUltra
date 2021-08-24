package io.github.lamprose.mi_ultra.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import de.robv.android.xposed.XposedBridge
import io.github.lamprose.mi_ultra.utils.InitFields.TAG
import java.lang.Exception
import android.util.Log as ALog

object LogUtil {

    private const val maxLength = 4000
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    @JvmOverloads
    fun toast(msg: String, force: Boolean = true) {
        if (!force && OwnSP.moduleSP?.getBoolean("showLogToast", false) == true) return

        handler.post {
            Toast.makeText(InitFields.appContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    private fun doLog(
        f: (String, String) -> Int,
        tag: String = TAG,
        obj: Any?,
        toXposed: Boolean = false,
        toToast: Boolean = true
    ) {
        val str = if (obj is Throwable) ALog.getStackTraceString(obj) else obj.toString()
        if (str.length > maxLength) {
            val chunkCount: Int = str.length / maxLength
            for (i in 0..chunkCount) {
                val max: Int = 4000 * (i + 1)
                if (max >= str.length) {
                    doLog(
                        f,
                        tag = tag,
                        obj = str.substring(maxLength * i),
                        toXposed = toXposed,
                        toToast = toToast
                    )
                } else {
                    doLog(
                        f,
                        tag = tag,
                        obj = str.substring(maxLength * i, max),
                        toXposed = toXposed,
                        toToast = toToast
                    )
                }
            }
        } else {
            f(tag, str)
            if (toToast) toast(str, false)
            if (toXposed) XposedBridge.log("$tag : $str")
        }
    }

    @JvmStatic
    fun _d(obj: Any?, tag: String = TAG) {
        doLog(ALog::d, tag = tag, obj = obj, toXposed = false, toToast = false)
    }

    @JvmStatic
    @JvmOverloads
    fun d(obj: Any?, tag: String = TAG) {
        doLog(ALog::d, tag = tag, obj = obj)
    }

    @JvmStatic
    @JvmOverloads
    fun i(obj: Any?, tag: String = TAG) {
        doLog(ALog::i, tag = tag, obj = obj)
    }

    @JvmStatic
    @JvmOverloads
    fun e(obj: Any?, tag: String = TAG) {
        doLog(ALog::e, tag = tag, obj = obj, toXposed = true)
    }

    @JvmStatic
    @JvmOverloads
    fun v(obj: Any?, tag: String = TAG) {
        doLog(ALog::v, tag = tag, obj = obj)
    }

    @JvmStatic
    @JvmOverloads
    fun w(obj: Any?, tag: String = TAG) {
        doLog(ALog::w, tag = tag, obj = obj)
    }
}