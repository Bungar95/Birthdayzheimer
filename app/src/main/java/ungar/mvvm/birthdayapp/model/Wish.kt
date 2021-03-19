package ungar.mvvm.birthdayapp.model

import com.google.gson.annotations.SerializedName

data class Wish(
    @SerializedName("quote")
    val quote: String?,
    @SerializedName("relation")
    val relation: String?
) {
}