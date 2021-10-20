package io.github.lamprose.mi_ultra.view

import android.view.MenuItem
import androidx.preference.Preference
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.utils.InitFields
import io.github.lamprose.mi_ultra.utils.Utils
import io.github.lamprose.mi_ultra.view.base.BasePreferenceFragment

class PowerKeeperFragment : BasePreferenceFragment(R.string.pref_main_power_keeper_title) {
    override fun xmlId(): Int = R.xml.perference_power_keeper

    override fun menuId(): Int = R.menu.power_keeper

    override fun menuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> getMainActivity().showAbout()
            R.id.menu_goto_power_keeper -> Utils.goToAppManger(
                getMainActivity(),
                InitFields.powerKeeper
            )
        }
        return true
    }

    override fun initData() {
        findPreference<Preference>("power_keeper_block_view")?.setOnPreferenceClickListener {
            Utils.execProcess(
                "am start -n com.miui.powerkeeper/.ui.CloudInfoActivity --es \"caller\" \"contacts\"",
                isExit = false
            )
            true
        }
    }
}