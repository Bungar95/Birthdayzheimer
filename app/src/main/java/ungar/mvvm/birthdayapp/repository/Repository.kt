package ungar.mvvm.birthdayapp.repository

import dagger.Provides
import retrofit2.Call
import retrofit2.Response
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.network.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

class Repository @Inject constructor() {

    suspend fun getWishes(): Response<Wish> {
        return RetrofitInstance.api.getQuotes()
    }
}