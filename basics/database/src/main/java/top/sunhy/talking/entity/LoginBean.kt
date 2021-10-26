package top.sunhy.talking.entity
import com.google.gson.annotations.SerializedName


data class LoginBean(
    @SerializedName("token")
    var token: String = "",
    @SerializedName("username")
    var username: String = ""
)