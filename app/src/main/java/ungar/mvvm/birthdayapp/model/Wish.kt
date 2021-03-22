package ungar.mvvm.birthdayapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wish(
    @Expose
    @SerializedName("quote")
    val quote: String?,
    @Expose
    @SerializedName("relation")
    val relation: String?
) : Parcelable {
}