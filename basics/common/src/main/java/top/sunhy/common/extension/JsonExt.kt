package top.sunhy.common.extension

import com.alibaba.fastjson.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

fun JSONObject.toRequestBody(): RequestBody {
    return RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), this.toString())
}