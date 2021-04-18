package ungar.mvvm.birthdayapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "wish_table")
@Parcelize
data class Wish(
    @Expose
    @SerializedName("quote")
    val quote: String?,
    @Expose
    @SerializedName("relation")
    val relation: String?,
    @Expose
    @SerializedName("usergenerated")
    val userGenerated: Boolean = false,
    val useful: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
}