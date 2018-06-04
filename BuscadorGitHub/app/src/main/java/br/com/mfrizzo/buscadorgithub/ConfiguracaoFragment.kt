package br.com.mfrizzo.buscadorgithub

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

class ConfiguracaoFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_github)

        for (i in 0 until preferenceScreen.preferenceCount) {
            val preference = preferenceScreen.getPreference(i)
            atualizarPreferenceSumary(preference)
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, key: String?) {
        val preference = findPreference(key)

        if (preference != null) {
            atualizarPreferenceSumary(preference)
        }
    }

    fun atualizarPreferenceSumary(preference: Preference) {
        if (preference is ListPreference) {
            val corSelecionada = preferenceScreen.sharedPreferences
                    .getString(preference.key, "")
            val indexSelecionado = preference.findIndexOfValue(corSelecionada)
            var tituloSelecionado = preference.entries[indexSelecionado]
            preference.summary = tituloSelecionado
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}