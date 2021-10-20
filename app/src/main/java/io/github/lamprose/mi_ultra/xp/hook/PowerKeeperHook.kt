package io.github.lamprose.mi_ultra.xp.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.utils.OwnSP.valueEqualDo
import io.github.lamprose.mi_ultra.utils.ktx.replaceMethod

class PowerKeeperHook(lpparam: XC_LoadPackage.LoadPackageParam) : BaseHook(lpparam) {
    override fun hook() {
        "power_keeper_block_cloud_fps_config".valueEqualDo(true) {
            "com.miui.powerkeeper.statemachine.DisplayFrameSetting".let {
                it.replaceMethod(
                    "setScreenEffect",
                    String::class.java,
                    Int::class.java,
                    Int::class.java
                ) {
                    null
                }
                it.replaceMethod(
                    "setScreenEffectInternal",
                    Int::class.java,
                    Int::class.java,
                    String::class.java
                ) {
                    null
                }
            }
        }
    }
}