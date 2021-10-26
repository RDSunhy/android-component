package top.sunhy.network

import com.blankj.utilcode.util.GsonUtils
import retrofit2.Response

/**
 * Api 响应通用类
 */
sealed class ApiResponse<T> {
    companion object {
        
        fun <T> createError(response: Response<T>): ApiErrorResponse<T> {
            val errorResponse = response.errorBody()?.string()
            val errorMsg = if (errorResponse.isNullOrEmpty()) {
                DefaultErrorResponse(response.code().toString(), response.message())
            } else {
                try {
                    GsonUtils.fromJson(errorResponse, DefaultErrorResponse::class.java)
                }catch (e: Exception){
                    DefaultErrorResponse(response.code().toString(), null)
                }
            }
            return ApiErrorResponse(errorMsg)
        }

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(DefaultErrorResponse(null, error.message))
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val errorResponse = response.errorBody()?.string()
                val errorMsg = if (errorResponse.isNullOrEmpty()) {
                    DefaultErrorResponse(response.code().toString(), response.message())
                } else {
                    try {
                        GsonUtils.fromJson(errorResponse, DefaultErrorResponse::class.java)
                    }catch (e: Exception){
                        DefaultErrorResponse(null, null)
                    }
                }
                ApiErrorResponse(errorMsg)
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val error: DefaultErrorResponse) : ApiResponse<T>()

/**
 * 接受 api 错误信息实体类
 * 需要增加字段在这里扩展
 * 在 [ApiResponse.createError] 中接收字段
 */
data class DefaultErrorResponse(
    var code: String? = null,
    var message: String? = null,
    var destroyCode: String? = null

)
