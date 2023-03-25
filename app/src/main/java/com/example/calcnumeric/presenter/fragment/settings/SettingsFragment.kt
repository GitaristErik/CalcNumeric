package com.example.calcnumeric.presenter.fragment.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.calcnumeric.R
import kotlinx.coroutines.launch


class SettingsFragment : PreferenceFragmentCompat() {

    private val languageService: LanguageService by lazy { LanguageService(resources) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        lifecycleScope.launch {
            setupLanguagePreference()
        }
    }

    private fun setupLanguagePreference() {
        findPreference<ListPreference>("language")!!.apply {
            entries = languageService.languageNames.toTypedArray()
            entryValues = languageService.languageTags.toTypedArray()
            val language = languageService.defaultLanguage
            setDefaultValue(language.toLanguageTag())
            summary = language.displayName
        }.setOnPreferenceChangeListener { _, value ->
//            LanguageService.saveAndSetLanguage(requireContext(), value as String)
            LanguageService.setLanguage(value as String)
            true
        }
    }
}