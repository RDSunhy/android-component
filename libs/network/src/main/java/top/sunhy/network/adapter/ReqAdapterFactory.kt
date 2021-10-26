package top.sunhy.network.adapter

import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ReqAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Req::class.java) return null
        check(returnType is ParameterizedType) { "Resource must have generic type (e.g., Resource<ResponseBody>)" }
        val responseType = getParameterUpperBound(0, returnType)
        return ReqAdapter<Any>(responseType)
    }
}
