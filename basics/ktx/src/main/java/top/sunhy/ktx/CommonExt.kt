package top.sunhy.ktx

import com.blankj.utilcode.util.ConvertUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val Float.dp: Float
    get() = ConvertUtils.dp2px(this).toFloat()

val Int.dp: Int
    get() = ConvertUtils.dp2px(this.toFloat())

val Float.sp: Float
    get() = ConvertUtils.sp2px(this).toFloat()

val Int.sp: Int
    get() = ConvertUtils.sp2px(this.toFloat())


/**
 * use for list<T> fromat
 */
inline fun <reified T> fromJson(json: String): T = Gson().fromJson<T>(json, object : TypeToken<T>() {}.type)

