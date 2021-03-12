package ungar.mvvm.birthdayapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import kotlinx.android.synthetic.main.fragment_settings.*
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.model.Birthday
import ungar.mvvm.birthdayapp.ui.birthdays.BirthdaysViewModel
import ungar.mvvm.birthdayapp.ui.settings.SettingsViewModel
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: BirthdaysViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navigation.setupWithNavController(navController)

        var nameValue = "error"
        var dateValue: LocalDate = LocalDate.of(1970, 1, 1)
        var genderValue: Int = 0
        var dateDialog: MaterialDialog? = null
        val endDate = Calendar.getInstance()
        var currentDate = Calendar.getInstance()

        fab_add_birthday.setOnClickListener {

            // Show a bottom sheet containing the form to insert a new event
            val dialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                cornerRadius(res = R.dimen.rounded_corners)
                title(R.string.new_birthday)
                icon(R.drawable.ic_baseline_cake_24)
                message(R.string.new_birthday_description)
                customView(R.layout.dialog_add_birthday)
                //getActionButton(WhichButton.POSITIVE).isEnabled = false

                positiveButton(R.string.add_birthday) {
                    nameValue = birthdayName.text.toString()
                    genderValue = radioGroup_gender.checkedRadioButtonId
                    val newBirthday = Birthday(
                        nameValue, dateValue.dayOfMonth, dateValue.monthValue, dateValue.year, genderValue, viewModel.determineProfilePicture(genderValue)
                    )
                    viewModel.createBirthday(newBirthday)
                    dismiss()
                }

                negativeButton(R.string.cancel_birthday) {
                    dismiss()
                }

                birthdayDate.setOnClickListener {
                    // Prevent double dialogs on fast click
                    if(dateDialog == null) {
                        dateDialog = MaterialDialog(this.windowContext).show {
                            cancelable(false)
                            cancelOnTouchOutside(false)
                            datePicker(maxDate = endDate, currentDate = currentDate) { _, date ->
                                val year = date.get(Calendar.YEAR)
                                val month = date.get(Calendar.MONTH) + 1
                                val day = date.get(Calendar.DAY_OF_MONTH)
                                dateValue = LocalDate.of(year, month, day)
                                // If ok is pressed, the last selected date is saved if the dialog is reopened
                                currentDate.set(year, month - 1, day)
                                //birthdayDate.setText(currentDate.toString()) crashes
                            }
                        }

                        Handler(Looper.getMainLooper()).postDelayed({ dateDialog = null }, 750)
                    }
                }
            }
        }
    }
}