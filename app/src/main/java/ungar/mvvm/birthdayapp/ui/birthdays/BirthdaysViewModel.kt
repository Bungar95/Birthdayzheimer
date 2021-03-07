package ungar.mvvm.birthdayapp.ui.birthdays

import android.widget.TextView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.model.Birthday
import ungar.mvvm.birthdayapp.model.BirthdayDao
import ungar.mvvm.birthdayapp.model.PreferencesManager
import java.time.LocalDate
import java.util.*

@HiltViewModel
class BirthdaysViewModel constructor(
    private val birthdayDao: BirthdayDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val preferencesFlow = preferencesManager.preferencesFlow

    val birthdays = birthdayDao.getBirthdays().asLiveData()

    fun onOptionsCardChanged(optionsCardOpen: Boolean) = viewModelScope.launch {
        preferencesManager.updateOptionsCardOpen(optionsCardOpen)
    }

    // Launching new coroutines to insert the data in a non-blocking way

    fun createBirthday(birthday: Birthday) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.insert(birthday)
    }

    fun updateBirthday(birthday: Birthday) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.update(birthday)
    }

    fun deleteBirthday(birthday: Birthday) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.delete(birthday)
    }

    fun birthdaysCount(){
        birthdayDao.getBirthdaysCount()
    }

    fun birthdayCountdown(birthday: Birthday) : String{
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1 // calendar.month starts with january=0
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        return when{
            birthday.month == currentMonth -> {
                when{
                    birthday.day == currentDay -> "IT'S THEIR BIRTHDAY!"
                    birthday.day < currentDay -> "${currentDay-birthday.day} days since their birthday"
                    else -> "${birthday.day-currentDay} days to go"
                }
            }
            birthday.month < currentMonth -> {
                "${currentMonth-birthday.month} months since"
            }
            else -> "${birthday.month-currentMonth} months to go"
        }

    }

    fun setYearEditText(birthday: Birthday, editable: TextView){
        when(Calendar.getInstance().get(Calendar.MONTH)+1){
            birthday.month -> {
                if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < birthday.day)
                    editable.text = (Calendar.getInstance().get(Calendar.YEAR) - birthday.year - 1).toString()
                else
                    editable.text = (Calendar.getInstance().get(Calendar.YEAR) - birthday.year).toString()
            }
            else ->
                if(Calendar.getInstance().get(Calendar.MONTH)+1 < birthday.month)
                    editable.text = (Calendar.getInstance().get(Calendar.YEAR) - birthday.year - 1).toString()
                else
                    editable.text = (Calendar.getInstance().get(Calendar.YEAR) - birthday.year).toString()
        }
    }

    fun localDatetoCalendar(localDate: LocalDate): Calendar {
        var calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(localDate.year, localDate.monthValue -1, localDate.dayOfMonth)
        return calendar
    }


}