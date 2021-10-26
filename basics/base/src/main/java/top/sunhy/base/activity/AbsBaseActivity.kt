package top.sunhy.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import top.sunhy.base.core.IActivity
import top.sunhy.base.core.IViewModel


abstract class AbsBaseActivity<B : ViewDataBinding, VM : IViewModel> : LogActivity(),
    IActivity, CoroutineScope by MainScope() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun initViewModel(): VM
    protected abstract fun initParams(savedInstanceState: Bundle?)
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun initObserver()
}