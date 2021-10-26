package top.sunhy.common.utils

import android.content.Context
import com.blankj.utilcode.util.SPUtils
import top.sunhy.common.config.Constant
import top.sunhy.talking.entity.LoginBean

object SpUtils {

    fun getInstance(): SPUtils {
        return SPUtils.getInstance(Constant.SP_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String{
       return getInstance().getString(Constant.SP_KEY_TOKEN, "")
    }

    fun saveToken(data: LoginBean){
        getInstance().put(Constant.SP_KEY_TOKEN, data.token)
        getInstance().put(Constant.SP_KEY_USERNAME, data.username)
    }

    fun saveLastLoginEmail(email: String){
        getInstance().put(Constant.SP_KEY_LAST_EMAIL, email)
    }

    fun getLastLoginEmail(): String{
        return getInstance().getString(Constant.SP_KEY_LAST_EMAIL, "")
    }
}