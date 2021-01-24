package ungar.mvvm.datepicker.ui.birthdays

import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ungar.mvvm.datepicker.model.Birthday
import ungar.mvvm.datepicker.model.BirthdayDao
import ungar.mvvm.datepicker.ui.datepicker.DatePickerDialogFragment

class BirthdaysViewModel @ViewModelInject constructor(
    private val birthdayDao: BirthdayDao
) : ViewModel() {

    val birthdays = birthdayDao.getBirthdays().asLiveData()

    fun addBirthday(birthday: Birthday) = viewModelScope.launch {
        birthdayDao.insert(birthday)
    }

}