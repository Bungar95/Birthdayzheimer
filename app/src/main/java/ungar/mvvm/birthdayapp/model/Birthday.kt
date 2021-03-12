package ungar.mvvm.birthdayapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ungar.mvvm.birthdayapp.R

@Entity(tableName = "birthday_table")
@Parcelize
data class Birthday(
    val name: String,
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val gender: Int = 0,
    val profilePicture: Int = R.drawable.m1,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
    ): Parcelable
