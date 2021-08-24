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

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.utils.ktx.callMethodAs
import io.github.lamprose.mi_ultra.utils.ktx.hookAfterMethod

@SuppressLint("StaticFieldLeak")
object InitFields {
    lateinit var appContext: Context
    lateinit var classLoader: ClassLoader
    const val SP_NAME = "MI_Ultra"
    const val TAG = "MI_Ultra"
    const val systemUI = "com.android.systemui"
    const val home = "com.miui.home"

    fun initHandleLoadPackage(
        lpparam: XC_LoadPackage.LoadPackageParam,
        initContext: Boolean = true
    ) {
        classLoader = lpparam.classLoader
        if (initContext)
            "android.app.Application".hookAfterMethod("attach", Context::class.java) {
                appContext = it.args[0] as Context
            }
    }
}