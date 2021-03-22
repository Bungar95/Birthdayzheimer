package ungar.mvvm.birthdayapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.first
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.ui.birthdays.BirthdaysViewModel
import ungar.mvvm.birthdayapp.ui.settings.SettingsViewModel

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: BirthdaysViewModel by viewModels()
    private val sViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            checkTheme()
        }
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navigation.setupWithNavController(navController)

    }

    private suspend fun checkTheme() {
        when (sViewModel.preferencesFlow.first().theme) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}