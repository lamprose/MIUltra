package io.github.lamprose.mi_ultra.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Android 系统属性读取工具类
 * Created by Zhuliya on 2018/10/22
 */
object SystemPropertyUtil {
    /**
     * 使用命令方式读取系统属性
     *
     * @param propName propName
     * @return String
     */
    fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            //Log.e("Unable to read sysprop " + propName, ex);
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    //Log.e("Exception while closing InputStream", e);
                }
            }
        }
        return line
    }

    /**
     * 读取系统属性，装载至Properties
     *
     * @return Prop
     */
    val property: Properties
        get() {
            val properties = Properties()
            try {
                val p = Runtime.getRuntime().exec("getprop")
                properties.load(p.inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return properties
        }
}