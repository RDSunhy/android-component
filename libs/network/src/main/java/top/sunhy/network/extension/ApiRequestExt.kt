package top.sunhy.network.extension

import top.sunhy.network.ApiErrorResponse
import top.sunhy.network.ApiResponse
import top.sunhy.network.adapter.Req
import top.sunhy.network.adapter.ReqCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }
        })
    }
}

suspend fun <T> Req<T>.await(): ApiResponse<T> {
    return suspendCoroutine { continuation ->
        request(object : ReqCallback<T> {
            override fun onSuccess(data: ApiResponse<T>) {
                continuation.resume(data)
            }

            override fun onError(error: ApiErrorResponse<T>) {
                continuation.resume(error)
            }
        })
    }
}
