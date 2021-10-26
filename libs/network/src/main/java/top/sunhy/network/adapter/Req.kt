package top.sunhy.network.adapter


interface Req<T> {
    /**
     * 取消请求
     */
    fun cancel()

    /**
     * 发起请求
     */
    fun request(callback: ReqCallback<T>)

    /**
     * 克隆
     */
    fun clone(): Req<T>
}