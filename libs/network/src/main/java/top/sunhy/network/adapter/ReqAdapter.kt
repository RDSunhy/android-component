package top.sunhy.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a Resource of ApiResponse.
 */
class ReqAdapter<R>(private val responseType: Type): CallAdapter<R, Req<R>> {
    override fun adapt(call: Call<R>): Req<R> {
        return ReqImpl(call)
    }

    override fun responseType(): Type = responseType
}