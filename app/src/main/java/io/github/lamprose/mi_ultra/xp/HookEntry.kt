package io.github.lamprose.mi_ultra.xp

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.BuildConfig
import io.github.lamprose.mi_ultra.utils.InitFields
import io.github.lamprose.mi_ultra.utils.ktx.findClass
import io.github.lamprose.mi_ultra.utils.ktx.setReturnConstant
import io.github.lamprose.mi_ultra.xp.hook.*

class HookEntry : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        when (lpparam.packageName) {
            BuildConfig.APPLICATION_ID -> {
                InitFields.initHandleLoadPackage(lpparam, false)
                "${BuildConfig.APPLICATION_ID}.utils.Utils".findClass()
                    .setReturnConstant("isModuleActive", result = true)
            }
            InitFields.systemUI -> {
                SystemUIHook(lpparam).hook()
            }
            InitFields.home -> {
                HomeHook(lpparam).hook()
            }
            InitFields.powerKeeper->{
                PowerKeeperHook(lpparam).hook()
            }
            else -> return
        }
    }
}