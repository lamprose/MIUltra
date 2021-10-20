package io.github.lamprose.mi_ultra.view

import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.utils.Utils
import io.github.lamprose.mi_ultra.view.base.BasePreferenceFragment

class MainFragment(
    private val handler: Handler
) : BasePreferenceFragment(R.string.app_name) {

    override fun xmlId(): Int = R.xml.preference_main
    override fun menuId(): Int = R.menu.main


    override fun initData() {
        findPreference<Preference>("system_ui")?.setOnPreferenceClickListener {
            getMainActivity().go(SystemUIFragment())
            true
        }
        findPreference<Preference>("home")?.setOnPreferenceClickListener {
            getMainActivity().go(HomeFragment())
            true
        }
        findPreference<Preference>("power_keeper")?.setOnPreferenceClickListener {
            getMainActivity().go(PowerKeeperFragment())
            true
        }
        findPreference<SwitchPreference>("hide_app_icon")?.apply {
            isChecked =
                pref.getBoolean("hide_app_icon", false) == true
            setOnPreferenceChangeListener { _, newValue ->
                handler.sendEmptyMessageDelayed(if (newValue as Boolean) 0 else 1, 1000)
                true
            }
        }
        findPreference<SwitchPreference>("module_status")?.apply {
            isChecked = Utils.isModuleActive()
        }
    }

    override fun menuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> getMainActivity().showAbout()
        }
        return true
    }

}