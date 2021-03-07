package ungar.mvvm.birthdayapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.ui.birthdays.BirthdaysViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup_language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                0 -> viewModel.onLanguageChanged("en")
                1 -> viewModel.onLanguageChanged("hr")
            }
        }
    }
}