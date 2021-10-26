package top.sunhy.common.api.interceptor

import com.blankj.utilcode.util.DeviceUtils
import okhttp3.Interceptor
import okhttp3.Response
import top.sunhy.common.utils.SpUtils
import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
            .addHeader("token", SpUtils.getToken())
            .addHeader("deviceId", DeviceUtils.getUniqueDeviceId())
            .build()
        return chain.proceed(request)
    }
}