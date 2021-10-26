package top.sunhy.network.extension

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import top.sunhy.network.Resource
import top.sunhy.network.Status
import top.sunhy.network.DefaultErrorResponse

fun <T> Resource<T>.deal(
    error: (msg: DefaultErrorResponse?) -> Unit = {
        it?.message?.let { msg ->
            if (!msg.isNullOrEmpty()){
                ToastUtils.showShort(msg)
            }
        }
    },
    isNull: () -> Unit = {},
    success: (data: T) -> Unit = {}
) {
    when (this.status) {
        Status.SUCCESS -> {
            if (this.data == null || (this.data is Collection<*> && (this.data as Collection<*>).isNullOrEmpty())) {
                isNull()
            } else {
                success(this.data!!)
            }
        }
        Status.ERROR -> {
            LogUtils.e("Resource Error:", this.error)
            error(this.error)
        }
    }
}