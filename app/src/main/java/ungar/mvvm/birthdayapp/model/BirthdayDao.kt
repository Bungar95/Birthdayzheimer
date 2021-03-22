package ungar.mvvm.birthdayapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {

    //Birthdays

    @Query("SELECT * FROM birthday_table")
    fun getBirthdays(): Flow<List<Birthday>>

    @Query("SELECT * FROM birthday_table WHERE id=:id")
    fun getSingleBirthday(id: Int): Birthday

    @Query("SELECT * FROM birthday_table WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name")
    fun getOrderedBirthdaysByName(searchQuery: String): Flow<List<Birthday>>

    @Query("SELECT COUNT(id) FROM birthday_table")
    fun getBirthdaysCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(birthday: Birthday)

    @Update
    suspend fun update(birthday: Birthday)

    @Delete
    suspend fun delete(birthday: Birthday)

    //Notes

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}