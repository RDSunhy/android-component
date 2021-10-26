package top.sunhy.base.vb

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.drakeet.multitype.ItemViewDelegate
import top.sunhy.base.utils.ActivityManager

/**
 * 封装 Multi ItemViewDelegate
 * 转换为 类似 BRVAH 使用
 * 更加方便易用
 */
abstract class BaseViewBinder<T, DB : ViewDataBinding>: ItemViewDelegate<T, BaseBindingViewHolder<DB>> {

    private var mLayoutId = -1

    constructor(layoutId: Int): super(){
        this.mLayoutId = layoutId
    }

    var datas: MutableList<Any>
        private set(value) {
            datas = value
        }
        get() {
            return (adapterItems as MutableList<Any>)
        }

    var context: Context
        private set(value) {
            context = value
        }
        get() {
            checkNotNull(mContext) {
                "Please get it after onCreateViewHolder()"
            }
            return mContext!!
        }

    private var mContext: Context? = null

    fun <A> getAdapter(): A {
        return adapter as A
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): BaseBindingViewHolder<DB> {
        mContext = context
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: DB = DataBindingUtil.inflate(inflater, mLayoutId, parent, false)
        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<DB>, item: T) {
        convert(holder, item)
    }

    override fun onViewAttachedToWindow(holder: BaseBindingViewHolder<DB>) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseBindingViewHolder<DB>) {
        super.onViewDetachedFromWindow(holder)
    }

    abstract fun convert(holder: BaseBindingViewHolder<DB>, item: T)

    protected fun getActivity(): Activity{
       return ActivityManager.getTop()
    }
}