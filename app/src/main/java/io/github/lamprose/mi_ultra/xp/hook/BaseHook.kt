package io.github.lamprose.mi_ultra.xp.hook

import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.BuildConfig
import io.github.lamprose.mi_ultra.utils.InitFields
import io.github.lamprose.mi_ultra.utils.OwnSP

abstract class BaseHook(lpparam: XC_LoadPackage.LoadPackageParam, autoInit: Boolean = true) {

    init {
        InitFields.initHandleLoadPackage(lpparam, autoInit)
    }

    abstract fun hook()

}