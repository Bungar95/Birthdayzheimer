package ungar.mvvm.birthdayapp.repository

import retrofit2.Response
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.network.RetrofitInstance
import javax.inject.Inject

class Repository @Inject constructor() {

    suspend fun getWishes(): Response<List<Wish>> {
        return RetrofitInstance.api.getQuotes()
    }
}