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
import android.content.SharedPreferences
import android.util.Log
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import io.github.lamprose.mi_ultra.BuildConfig
import io.github.lamprose.mi_ultra.utils.InitFields.SP_NAME
import java.lang.reflect.Method

object OwnSP {
    val moduleSP: XSharedPreferences? by lazy {
        val pref = XSharedPreferences(BuildConfig.APPLICATION_ID, SP_NAME)
        return@lazy if (pref.file.canRead()) pref else {
            XposedBridge.log("pref is can't be read")
            null
        }
    }

    fun <T> String.valueTrueDo(value:T,hook: () -> Unit){
        moduleSP?.let {
            when (value) {
                is Int -> if (it.getInt(this, 0) == value) hook()
                is String -> if (it.getString(this, "") == value) hook()
                is Long -> if (it.getLong(this, 0L) == value) hook()
                is Boolean -> if (it.getBoolean(this, false) == value) hook()
            }
        }
    }
}