package top.sunhy.ktx

//import com.alibaba.fastjson.JSONObject

/**
 * 判断字符串不为空
 */
fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

/**
 * 判断字符串是否为 json 字符串
 */
/*fun CharSequence?.isJson(): Boolean {
    if(this.isNullOrEmpty()){
        return false
    }
    try {
        JSONObject.parseObject(this.toString())
    }catch (e: Exception){
        try {
            JSONObject.parseArray(this.toString())
        }catch (e: Exception){
            return false
        }
    }
    return true
}*/

/**
 * 判断字符串是否为 true
 */
fun CharSequence?.isTrue(): Boolean {
    if(this.isNullOrEmpty()){
        return false
    }
    return this == "true"
}