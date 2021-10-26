package top.sunhy.common.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import top.sunhy.network.adapter.Req
import top.sunhy.talking.entity.DefaultResponse
import top.sunhy.talking.entity.LoginBean
import top.sunhy.talking.entity.UserBean

interface LoginApiService {

    /**
     * 登录 - 邮箱登录（验证码、密码）
     */
    @POST("user/login")
    fun loginByEmail(@Body body: RequestBody): Req<LoginBean>

    /**
     * 注册 - 邮箱验证码注册
     */
    @POST("user/regist")
    fun registByEmail(@Body body: RequestBody): Req<DefaultResponse>

    /**
     * 发送邮箱验证码
     */
    @POST("user/sendEmailCode")
    fun sendEmailCode(@Body body: RequestBody): Req<DefaultResponse>

    /**
     * 当前用户信息
     */
    @POST("user/info")
    fun getUserInfo(): Req<UserBean>
}