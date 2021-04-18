package ungar.mvvm.birthdayapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "birthday_table")
@Parcelize
data class Birthday(
    val name: String,
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val gender: Int = 0,
    val profilePicture: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    //private val c = Calendar.getInstance().set(year, month, day)
    //val birthdayDate: String
     //   get() = DateFormat.getDateInstance().format(c)
}
