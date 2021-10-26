package top.sunhy.network.adapter

import top.sunhy.network.ApiErrorResponse
import top.sunhy.network.ApiResponse

/**
 * 自定义请求回调
 * 对应 retrofit call.enqueue 中的回调
 */
interface ReqCallback<T> {
    fun onSuccess(data: ApiResponse<T>)
    fun onError(error: ApiErrorResponse<T>)
}