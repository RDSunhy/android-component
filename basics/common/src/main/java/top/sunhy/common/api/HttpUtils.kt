package top.sunhy.common.api

import top.sunhy.common.api.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.sunhy.base.application.BaseApplication
import top.sunhy.common.config.Constant
import top.sunhy.network.adapter.ReqAdapterFactory
import java.util.concurrent.TimeUnit

object HttpUtils {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ReqAdapterFactory())
            .client(getOkHttpClient().build())
            .build()
    }

    val loginApi: LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
    val commonApi: CommonApiService by lazy { retrofit.create(CommonApiService::class.java) }

    private fun getOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(RequestInterceptor())
            if (BaseApplication.isDebug()){
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }
    }
}