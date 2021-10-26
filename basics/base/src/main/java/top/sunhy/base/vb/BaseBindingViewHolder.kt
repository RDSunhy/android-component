package top.sunhy.base.vb

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 封装 ViewHolder 使用 Databinding 绑定 更方便
 */
class BaseBindingViewHolder<DB : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {
    val dataBinding = DataBindingUtil.bind<DB>(view)
}