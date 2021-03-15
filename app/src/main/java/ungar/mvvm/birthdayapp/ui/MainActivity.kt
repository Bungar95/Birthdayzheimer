package ungar.mvvm.birthdayapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.datetime.datePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_birthday.*
import kotlinx.android.synthetic.main.fragment_birthdays.*
import kotlinx.coroutines.flow.first
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.model.Birthday
import ungar.mvvm.birthdayapp.ui.birthdays.BirthdaysViewModel
import ungar.mvvm.birthdayapp.ui.settings.SettingsViewModel
import java.time.LocalDate
import java.util.*

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
        when(sViewModel.preferencesFlow.first().theme) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}