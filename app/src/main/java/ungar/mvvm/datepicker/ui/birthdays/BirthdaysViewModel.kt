package ungar.mvvm.datepicker.ui.birthdays

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ungar.mvvm.datepicker.model.Birthday
import ungar.mvvm.datepicker.model.BirthdayDao
import java.util.*

class BirthdaysViewModel @ViewModelInject constructor(
    private val birthdayDao: BirthdayDao
) : ViewModel() {

    val birthdays = birthdayDao.getBirthdays().asLiveData()

    fun addBirthday(birthday: Birthday) = viewModelScope.launch {
        birthdayDao.insert(birthday)
    }
    
    fun birthdayCountdown(birthday: Birthday) : String{
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1 // calendar.month starts with january=0
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        return when{
            birthday.month == currentMonth -> {
                when{
                    birthday.day == currentDay -> "IT'S THEIR BIRTHDAY!"
                    birthday.day < currentDay -> "${currentDay-birthday.day} since their birthday!"
                    else -> "${birthday.day-currentDay} days to go!"
                }
            }
            birthday.month < currentMonth -> {
                "${currentMonth-birthday.month} months to go."
            }
            else -> "${birthday.month-currentMonth} months to go."
        }

    }

}