package top.sunhy.common.api

import retrofit2.http.GET
import retrofit2.http.Path
import top.sunhy.network.adapter.Req

interface CommonApiService {

    @GET("https://gank.io/api/v2/data/category/Girl/type/Girl/page/{page}/count/{count}")
    fun getImages(
        @Path("page") page: Int,
        @Path("count") count: Int
    ): Req<String>

    @GET("https://gank.io/api/v2/banners")
    fun getBanners(): Req<String>
}