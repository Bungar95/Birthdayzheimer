package ungar.mvvm.birthdayapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.flow.first

@HiltAndroidApp
class BirthdayApplication: Application() {

}