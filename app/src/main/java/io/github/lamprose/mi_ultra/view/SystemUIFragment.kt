package io.github.lamprose.mi_ultra.view

import android.view.MenuItem
import androidx.preference.DropDownPreference
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreference
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.view.base.BasePreferenceFragment
import java.io.DataOutputStream
import kotlin.system.exitProcess

class SystemUIFragment : BasePreferenceFragment(R.string.pref_main_system_ui_title) {
    override fun xmlId(): Int = R.xml.preference_system_ui
    override fun menuId(): Int = R.menu.system_ui

    override fun initData() {
        findPreference<SwitchPreference>("non_default_theme_notification_blur")?.isChecked =
            pref.getBoolean("non_default_theme_notification_blur", false)
        findPreference<SwitchPreference>("notification_icon_force_android_style")?.isChecked =
            pref.getBoolean("notification_icon_force_android_style", false)
        findPreference<SeekBarPreference>("notification_blur_radius")?.apply {
            value = pref.getInt("notification_blur_radius", 0)
        }
        findPreference<DropDownPreference>("notification_blur_style")?.apply {
            summary =
                try {
                    pref.getString(
                        "notification_blur_style",
                        null
                    )?.toInt()
                } catch (e: NumberFormatException) {
                    null
                }?.let {
                    resources.getStringArray(R.array.notification_blur_style)[it]
                } ?: ""
            setOnPreferenceChangeListener { _, value ->
                value.toString().toInt().let {
                    summary = resources.getStringArray(R.array.notification_blur_style)[it]
                    pref.edit().putInt(
                        "notification_blur_radius_light_color_burn",
                        resources.getIntArray(R.array.notification_blur_style_light_color_burn)[it]
                    )
                        .putInt(
                            "notification_blur_radius_dark_color_burn",
                            resources.getIntArray(R.array.notification_blur_style_dark_color_burn)[it]
                        )
                        .putInt(
                            "notification_blur_radius_light_color",
                            resources.getIntArray(R.array.notification_blur_style_light_color)[it]
                        )
                        .putInt(
                            "notification_blur_radius_dark_color",
                            resources.getIntArray(R.array.notification_blur_style_dark_color)[it]
                        ).apply()
                }
                true
            }
        }
    }


    override fun menuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> getMainActivity().showAbout()
            R.id.menu_reboot_systemui -> {
                val suProcess = Runtime.getRuntime().exec("su")
                val os = DataOutputStream(suProcess.outputStream)
                os.writeBytes("pkill -f com.android.systemui")
                os.flush()
                os.close()
                val exitValue = suProcess.waitFor()
                if (exitValue == 0) {
                    exitProcess(0)
                } else {
                    throw Exception()
                }
            }
        }
        return true
    }
}