package top.sunhy.base.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import top.sunhy.base.core.IFragment
import top.sunhy.base.vm.AbsViewModel

abstract class AbsBaseFragment<B : ViewDataBinding, VM : AbsViewModel> : LogFragment(), IFragment {

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun initViewModel(): VM
    protected abstract fun initParams(savedInstanceState: Bundle?)
    protected abstract fun initLoadSir()
    protected abstract fun firstLoad()
    protected abstract fun lazyLoad()
    protected abstract fun interrupt()
    protected abstract fun initListener()
    protected abstract fun initObserver()
}