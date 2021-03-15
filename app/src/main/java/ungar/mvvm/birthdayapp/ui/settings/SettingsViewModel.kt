package ungar.mvvm.birthdayapp.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
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

}