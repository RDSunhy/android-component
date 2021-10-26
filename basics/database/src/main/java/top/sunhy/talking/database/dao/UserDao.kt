package top.sunhy.talking.database.dao

import androidx.room.Dao
import androidx.room.Query
import top.sunhy.talking.database.Tables
import top.sunhy.talking.database.core.BaseDao
import top.sunhy.talking.entity.UserBean

@Dao
interface UserDao: BaseDao<UserBean> {

    @Query("DELETE FROM ${Tables.USER} WHERE id=:id")
    fun delete(id: String)
}