package ungar.mvvm.datepicker.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {

    @Query("SELECT * FROM birthday_table")
    fun getBirthdays() : Flow<List<Birthday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(birthday: Birthday)

    @Update
    suspend fun update(birthday: Birthday)

    @Delete
    suspend fun delete(birthday: Birthday)
}