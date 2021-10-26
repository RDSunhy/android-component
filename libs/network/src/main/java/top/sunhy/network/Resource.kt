package top.sunhy.network

/**
 * 网络请求结果包装类
 */
data class Resource<out T>(val status: Status, val data: T?, val error: DefaultErrorResponse?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: DefaultErrorResponse?, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}
