package ungar.mvvm.birthdayapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_birthdays.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.flow.first
import ungar.mvvm.birthdayapp.R

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            switch_nightMode.isChecked = viewModel.preferencesFlow.first().theme
        }

        switch_nightMode.setOnCheckedChangeListener{ _, checkedId ->
                when(checkedId) {
                    false -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    true -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }
        }
    }

    private fun setTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        viewModel.onThemeNightModeChanged(switch_nightMode.isChecked)
    }
}