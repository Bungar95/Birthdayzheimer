package ungar.mvvm.birthdayapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.model.PreferencesManager


@HiltViewModel
class SettingsViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun onLanguageChanged(app_language: String) = viewModelScope.launch {
        preferencesManager.updateLanguage(app_language)
    }
}