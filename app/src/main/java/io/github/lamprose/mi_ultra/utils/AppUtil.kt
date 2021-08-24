/*
 * Fuck Coolapk - Best present for 316 and 423
 * Copyright (C) 2020-2021
 * https://github.com/ejiaogl/FuckCoolapk
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNUGeneral Public License as
 * published by the Free Software Foundation; either version 3 of the License,
 * or any later version and our eula as published by ejiaogl.
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License and
 * eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/ejiaogl/FuckCoolapk/blob/master/LICENSE>.
 */

package io.github.lamprose.mi_ultra.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.Log
import io.github.lamprose.mi_ultra.utils.InitFields.TAG
import io.github.lamprose.mi_ultra.utils.InitFields.appContext


fun dp2px(dpValue: Float): Int =
    (dpValue * appContext.resources.displayMetrics.density + 0.5f).toInt()

fun sp2px(spValue: Float): Int =
    (spValue * appContext.resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun isNightMode(context: Context): Boolean =
    (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

inline fun getColorFix(block: () -> String): String {
    var string = block()
    while (string.length < 6) {
        string = "0$string"
    }
    return string
}

inline fun getColorFixWithHashtag(block: () -> String): String = "#${getColorFix(block)}"


fun reverseColor(mString: String): String =
    "${(255 - mString.substring(0, 2).toInt(16)).toString(16)}${
        (255 - mString.substring(2, 4).toInt(16)).toString(16)
    }${(255 - mString.substring(4, 6).toInt(16)).toString(16)}"

fun getVersionCode(): Long {
    return try {
        val packageManager: PackageManager = appContext.packageManager
        packageManager.getPackageInfo(appContext.packageName, 0).longVersionCode
    } catch (e: Exception) {
        Log.e(TAG, "getVersionName error:${e.message}")
        -1L
    }
}

fun getVersionName(): String? {
    return try {
        appContext.packageManager.getPackageInfo(appContext.packageName, 0).versionName
    } catch (e: Exception) {
        Log.e(TAG, "getVersionName error:${e.message}")
        null
    }
}