package top.sunhy.talking.mine.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.fragment.BaseFragment
import top.sunhy.talking.mine.MineViewModel
import top.sunhy.talking.mine.R
import top.sunhy.talking.mine.databinding.MineFragmentMainBinding
import top.sunhy.talking.routers.MineRouter

/**
 * Mine组件 入口
 */
@Route(path = MineRouter.FRAGMENT_MAIN)
class MineMainFragment: BaseFragment<MineFragmentMainBinding, MineViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.mine_fragment_main
    }

    override fun initViewModel(): MineViewModel {
        return getFragmentScopeViewModel(MineViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
    }

    override fun lazyLoad() {
    }

    override fun interrupt() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }
}