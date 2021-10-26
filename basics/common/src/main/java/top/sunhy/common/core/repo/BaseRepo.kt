package top.sunhy.common.core.repo

import top.sunhy.network.*
import top.sunhy.network.adapter.Req
import top.sunhy.network.extension.await

open class BaseRepo {

    /**
     * 不需要缓存的 api
     * 可以直接调用这个方法
     */
    suspend fun <T> execute(request: Req<T>): Resource<T> {
        var result = request.await()
        return when (result) {
            is ApiSuccessResponse -> {
                Resource.success(result.body)
            }
            is ApiEmptyResponse -> {
                Resource.success(null)
            }
            is ApiErrorResponse -> {
                Resource.error(result.error, null)
            }
            else -> Resource.error(DefaultErrorResponse(null, "BaseRepo.execute: result is null"), null)
        }
    }
}