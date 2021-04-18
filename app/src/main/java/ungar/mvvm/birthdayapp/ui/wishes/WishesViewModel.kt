package ungar.mvvm.birthdayapp.ui.wishes

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Response
import ungar.mvvm.birthdayapp.model.PreferencesManager
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.repository.WishRepository

class WishesViewModel @ViewModelInject constructor(
    private val wishRepository: WishRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val preferencesFlow = preferencesManager.preferencesFlow
    val wishes = wishRepository.getNetworkWishes().asLiveData()


    /*private val _myResponse: MutableLiveData<List<Wish>> = MutableLiveData()
    val myResponse: LiveData<List<Wish>>
        get() = _myResponse

    fun getQuotes(): List<Wish>? {
        viewModelScope.launch {
            val response: Response<List<Wish>> = wishRepository.getWishes()
            if (response.isSuccessful) {
                Log.d("Response ->", response.body().toString())
                _myResponse.value = response.body()
            } else
                Log.d("Response ->", response.errorBody().toString())
        }

        return _myResponse.value
    }*/

    fun onWishesCardChanged(wishesCardOpen: Boolean) = viewModelScope.launch {
        preferencesManager.updateWishesCardOpen(wishesCardOpen)
    }
}