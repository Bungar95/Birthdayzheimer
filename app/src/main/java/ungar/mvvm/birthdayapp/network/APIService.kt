package ungar.mvvm.birthdayapp.network

import retrofit2.Response
import retrofit2.http.GET
import ungar.mvvm.birthdayapp.model.Wish

interface APIService {

    @GET("/.json")
    suspend fun getQuotes(): Response<List<Wish>>

}