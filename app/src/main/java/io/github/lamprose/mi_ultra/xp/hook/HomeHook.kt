package io.github.lamprose.mi_ultra.xp.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.utils.OwnSP.valueTrueDo
import io.github.lamprose.mi_ultra.utils.ktx.findClass
import io.github.lamprose.mi_ultra.utils.ktx.setReturnConstant

class HomeHook(lpparam: XC_LoadPackage.LoadPackageParam) : BaseHook(lpparam) {
    override fun hook() {
        "widget_can_drag_to_pa".valueTrueDo(true) {
            "com.miui.home.launcher.Workspace".findClass()
                .setReturnConstant("canDragToPa", result = true)
        }

    }
}