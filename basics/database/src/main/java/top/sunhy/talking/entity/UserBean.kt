package top.sunhy.talking.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import top.sunhy.talking.database.Tables
import java.io.Serializable
import java.util.HashMap

@Entity(
    tableName = Tables.USER
)
class UserBean(
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",
    @SerializedName("bottleCount")
    var bottleCount: Int = 0,
    @SerializedName("createdAt")
    var createdAt: Long = 0,
    @SerializedName("fansCount")
    var fansCount: Int = 0,
    @SerializedName("followCount")
    var followCount: Int = 0,
    @SerializedName("leaveMessageCount")
    var leaveMessageCount: Int = 0,
    @SerializedName("showContactInfo")
    var showContactInfo: String = "",
    @SerializedName("showLeaveMessage")
    var showLeaveMessage: String = "",
    @SerializedName("showLeaveMessageDay")
    var showLeaveMessageDay: Int = 0,
    @SerializedName("topicCount")
    var topicCount: Int = 0,
    @SerializedName("username")
    var username: String = ""
) : Serializable