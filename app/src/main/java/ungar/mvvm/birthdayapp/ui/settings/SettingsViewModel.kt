package ungar.mvvm.birthdayapp.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.model.PreferencesManager

@HiltViewModel
//@Inject that replaced @ViewModelInject throws errors?
class SettingsViewModel @ViewModelInject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val preferencesFlow = preferencesManager.preferencesFlow

    fun onThemeNightModeChanged(theme: Boolean) = viewModelScope.launch {
        preferencesManager.updateThemeNightMode(theme)
    }

    fun checkNightMode(checked: Boolean) {
        return if (checked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}