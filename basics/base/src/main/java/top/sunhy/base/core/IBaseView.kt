package top.sunhy.base.core

interface IBaseView {
    /**
     * 加载中
     */
    fun showLoading()

    /**
     * 展示内容
     */
    fun showContent()

    /**
     * 错误页
     */
    fun showError()

    /**
     * 空白页
     */
    fun showEmpty()

    /**
     * 重试
     */
    fun onRetry()

    /**
     * show loading dialog
     */
    fun showLoadingDialog(msg: String?)

    /**
     * dismiss loading dialog
     */
    fun dismissLoadingDialog()
}