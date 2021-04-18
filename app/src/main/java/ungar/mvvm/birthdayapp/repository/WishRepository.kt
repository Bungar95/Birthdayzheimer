package ungar.mvvm.birthdayapp.repository

import androidx.room.withTransaction
import kotlinx.coroutines.delay
import retrofit2.Response
import ungar.mvvm.birthdayapp.model.BirthdayDatabase
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.network.RetrofitInstance
import ungar.mvvm.birthdayapp.util.networkBoundResource
import javax.inject.Inject

class WishRepository @Inject constructor(
    private val db: BirthdayDatabase
) {

    private val birthdayDao = db.birthdayDao()

    suspend fun getWishes(): List<Wish> {
        return RetrofitInstance.api.getQuotes()
    }

    fun getNetworkWishes() = networkBoundResource(
        query = {
            birthdayDao.getAllWishes()
        },
        fetch = {
            delay(2000)
            RetrofitInstance.api.getQuotes()
        },
        saveFetchResult = { wishes ->
            db.withTransaction {
                birthdayDao.deleteAllWishes()
                birthdayDao.insertWishes(wishes)
            }
        }
    )
}