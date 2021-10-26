package top.sunhy.common.core.popup

import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

interface IBasePopup: OnRefreshLoadMoreListener {

    /**
     * 如果 popup 弹窗中包含 recyclerview 通过这个方法返回
     */
    fun getMainRecyclerview(): RecyclerView?

    /**
     * 如果 popup 弹窗中包含 smartRefreshLayout 通过这个方法返回
     */
    fun getRefreshLayout(): SmartRefreshLayout?

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
}