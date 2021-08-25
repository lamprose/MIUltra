package io.github.lamprose.mi_ultra.view.base


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.preference.PreferenceFragmentCompat
import io.github.lamprose.mi_ultra.R
import io.github.lamprose.mi_ultra.utils.InitFields
import io.github.lamprose.mi_ultra.utils.Utils
import io.github.lamprose.mi_ultra.view.MainActivity


abstract class BasePreferenceFragment @JvmOverloads constructor(title: Any? = null) :
    PreferenceFragmentCompat() {
    private var title: Any? = null
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return menuClick(item)
    }

    @SuppressLint("WorldReadableFiles")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if (Utils.isModuleActive()) {
            preferenceManager.sharedPreferencesName = InitFields.SP_NAME
            preferenceManager.sharedPreferencesMode = Context.MODE_WORLD_READABLE
            setPreferencesFromResource(xmlId(), rootKey)
        } else {
            setPreferencesFromResource(xmlId(), rootKey)
            preferenceManager.preferenceScreen.isEnabled = false
        }
        refreshTitle()
        pref = try {
            getMainActivity().getSharedPreferences(
                InitFields.SP_NAME,
                Context.MODE_WORLD_READABLE
            )
        } catch (e: SecurityException) {
            preferenceManager.preferenceScreen.isEnabled = false
            null
        } ?: return
        initData()
    }

    fun getMainActivity(): MainActivity {
        return requireActivity() as MainActivity
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(menuId(), menu)
    }

    override fun onResume() {
        super.onResume()
        refreshTitle()
    }

    private fun refreshTitle() {
        title?.let {
            when (it) {
                is CharSequence,
                is String -> {
                    getMainActivity().findViewById<TextView>(R.id.toolbar_title).text =
                        it.toString()
                }
                is Int -> {
                    getMainActivity().findViewById<TextView>(R.id.toolbar_title).text =
                        requireActivity().getString(it)
                }
            }
        }
    }


    init {
        this.title = title
    }

    abstract fun xmlId(): Int

    abstract fun menuId(): Int

    abstract fun initData()

    abstract fun menuClick(item: MenuItem): Boolean
}