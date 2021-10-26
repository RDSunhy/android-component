package top.sunhy.network.adapter

import top.sunhy.network.ApiResponse
import retrofit2.Call
import retrofit2.Response


/**
 * 自定义Call实现类
 * 将 retrofit call 转换为自己想要的类型
 */
class ReqImpl<T>(private val call: Call<T>) :
    Req<T> {

    override fun cancel() {
        call.cancel()
    }

    override fun request(callback: ReqCallback<T>) {
        call.enqueue(object : retrofit2.Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onError(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                if (code in 200..299) {
                    callback.onSuccess(ApiResponse.create(response))
                } else {
                    //callback.onError(ApiErrorResponse("$code ${response.message()}"))
                    callback.onError(ApiResponse.createError(response))
                }
            }
        })
    }

    override fun clone(): Req<T> {
        return ReqImpl(call.clone())
    }
}
