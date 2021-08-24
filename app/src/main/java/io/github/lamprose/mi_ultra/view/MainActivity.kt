package io.github.lamprose.mi_ultra.view

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.view.base.BasePreferenceFragment
import java.io.DataOutputStream
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(mainLooper) {
            when (it.what) {
                0 -> {
                    packageManager.setComponentEnabledSetting(
                        ComponentName(
                            this@MainActivity,
                            this@MainActivity::class.java.name + "Alias"
                        ),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                }
                1 -> {
                    packageManager.setComponentEnabledSetting(
                        ComponentName(
                            this@MainActivity,
                            this@MainActivity::class.java.name + "Alias"
                        ),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )
                }
            }
            false
        }
        supportFragmentManager.beginTransaction()
            .add(
                R.id.main_fragment,
                MainFragment(handler!!)
            ).commit()
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun go(f: BasePreferenceFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, f)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    fun showAbout() {
        AlertDialog.Builder(this)
            .setTitle(R.string.menu_about_title)
            .setMessage(R.string.dialog_about_message)
            .setCancelable(true)
            .setPositiveButton(
                "CoolApk"
            ) { _, _ ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.coolapk.com/u/1033375")
                    )
                )
            }
            .setNegativeButton(
                "Github"
            ) { _, _ ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/lamprose/MIUIQuickOpen")
                    )
                )
            }
            .create().show()
    }
}