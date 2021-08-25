package io.github.lamprose.mi_ultra.xp.hook

import android.view.View
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.utils.LogUtil
import io.github.lamprose.mi_ultra.utils.OwnSP.valueEqualDo
import io.github.lamprose.mi_ultra.utils.ktx.*

class HomeHook(lpparam: XC_LoadPackage.LoadPackageParam) : BaseHook(lpparam) {
    override fun hook() {
        "home_widget_can_drag_to_pa".valueEqualDo(true) {
            "com.miui.home.launcher.Workspace".findClass()
                .setReturnConstant("canDragToPa", result = true)
        }
        "home_drawer_category_hide_all".valueEqualDo(true) {
            "com.miui.home.launcher.allapps.category.AllAppsCategoryListContainer"
                .hookAfterMethod("buildSortCategoryList") {
                    val list = it.result as ArrayList<*>
                    if (list.size > 1) {
                        list.removeAt(0)
                        it.result = list
                    }
                    LogUtil.i("home_drawer_category_hide_all success")
                }
        }
        "home_drawer_list_hide_edit".valueEqualDo(true) {
            "com.miui.home.launcher.allapps.AllAppsGridAdapter"
                .hookAfterMethod(
                    "onBindViewHolder",
                    "com.miui.home.launcher.allapps.AllAppsGridAdapter.ViewHolder".findClass(),
                    Int::class.java
                ) {
                    if ((it.args[0].callMethod("getItemViewType") as Int) == 64)
                        (it.args[0].getObjectField("itemView") as View).visibility = View.INVISIBLE
                    LogUtil.i("home_drawer_list_hide_edit success")
                }
        }
    }
}