package top.sunhy.talking.entity

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("message")
    var message: String = ""
)