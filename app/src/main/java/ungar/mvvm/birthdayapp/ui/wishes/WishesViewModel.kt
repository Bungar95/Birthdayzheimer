package ungar.mvvm.birthdayapp.ui.wishes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.network.RetrofitInstance
import ungar.mvvm.birthdayapp.repository.Repository

class WishesViewModel @ViewModelInject constructor(
    private val repository: Repository
    ): ViewModel() {

    var myResponse: MutableLiveData<Response<Wish>> = MutableLiveData()

    fun getQuotes() {
        viewModelScope.launch {
            val response: Response<Wish> = repository.getWishes()
            myResponse.value = response
        }
    }
}