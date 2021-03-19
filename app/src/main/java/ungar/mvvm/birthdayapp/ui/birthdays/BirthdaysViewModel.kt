package ungar.mvvm.birthdayapp.ui.birthdays

import android.widget.TextView
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.model.Birthday
import ungar.mvvm.birthdayapp.model.BirthdayDao
import ungar.mvvm.birthdayapp.model.PreferencesManager
import java.time.LocalDate
import java.util.*

@HiltViewModel
//@Inject that replaced @ViewModelInject in later release throws errors?
class BirthdaysViewModel @ViewModelInject constructor(
    private val birthdayDao: BirthdayDao,
    private val preferencesManager: PreferencesManager
): ViewModel(){

    //val birthdays = birthdayDao.getBirthdays().asLiveData()
    val birthdays: LiveData<List<Birthday>>
    val searchStringLiveData = MutableLiveData<String>()

    init {
        searchStringLiveData.value = ""
        birthdays = Transformations.switchMap(searchStringLiveData){ string ->
            birthdayDao.getOrderedBirthdaysByName(string).asLiveData()
        }
    }

    val preferencesFlow = preferencesManager.preferencesFlow


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

    fun determineProfilePicture(genderValue: Int) :Int {
        return when(genderValue) {
            R.id.radioBtn_male -> R.drawable.m1
            R.id.radioBtn_female -> R.drawable.w1
            else -> R.drawable.n1
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

    fun searchNameChanged(name: String) {
        searchStringLiveData.value = name
    }
}