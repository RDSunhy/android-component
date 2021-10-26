package top.sunhy.network.extension

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import top.sunhy.network.*
import top.sunhy.network.adapter.Req

abstract class BoundResource<ResultType>
@MainThread constructor() {

    suspend fun getResult(): Resource<ResultType> {
        var localData = getByLocal()
        if (shouldFetch(localData)) {
            if (getByApi() != null) {
                return when (val temp = getByApi()!!.await()) {
                    is ApiSuccessResponse -> {
                        NetworkExecutors.diskIO().execute {
                            saveApiData(temp.body)
                        }
                        Resource.success(temp.body)
                    }
                    is ApiEmptyResponse -> {
                        Resource.success(null)
                    }
                    is ApiErrorResponse -> {
                        Resource.error(temp.error, getByLocal())
                    }
                }
            } else {
                return Resource.error(
                    DefaultErrorResponse(null, "getByLocal() and getByApi() are both null!"),
                    getByLocal()
                )
            }
        } else {
            return if (localData != null) {
                Resource.success(localData)
            } else {
                Resource.success(null)
            }
        }
    }

    @WorkerThread
    protected abstract fun saveApiData(item: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun getByLocal(): ResultType?

    @MainThread
    protected abstract fun getByApi(): Req<ResultType>?
}
