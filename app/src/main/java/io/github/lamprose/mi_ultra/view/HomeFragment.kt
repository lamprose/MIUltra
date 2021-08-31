package io.github.lamprose.mi_ultra.view

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.preference.SwitchPreference
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.utils.InitFields
import io.github.lamprose.mi_ultra.utils.Utils
import io.github.lamprose.mi_ultra.view.base.BasePreferenceFragment

class HomeFragment : BasePreferenceFragment("系统桌面") {
    override fun xmlId(): Int = R.xml.preference_home

    override fun menuId(): Int = R.menu.home

    @SuppressLint("QueryPermissionsNeeded")
    override fun initData() {
        findPreference<SwitchPreference>("widget_can_drag_to_pa")?.apply {
            isEnabled = getMainActivity().packageManager.getInstalledPackages(0).find {
                it.packageName == "com.miui.home"
            }?.longVersionCode?.let {
                it >= 425003427L
            } == true
        }
    }

    override fun menuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> getMainActivity().showAbout()
            R.id.menu_reboot_home -> Utils.restartApp(InitFields.home)
        }
        return true
    }
}