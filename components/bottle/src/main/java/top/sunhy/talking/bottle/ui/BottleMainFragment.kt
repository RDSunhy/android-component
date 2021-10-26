package top.sunhy.talking.bottle.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.fragment.BaseFragment
import top.sunhy.talking.bottle.BottleViewModel
import top.sunhy.talking.bottle.R
import top.sunhy.talking.bottle.databinding.HomeFragmentMainBinding
import top.sunhy.talking.routers.BottleRouter

/**
 * Home组件 入口
 */
@Route(path = BottleRouter.FRAGMENT_MAIN)
class BottleMainFragment: BaseFragment<HomeFragmentMainBinding, BottleViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.home_fragment_main
    }

    override fun initViewModel(): BottleViewModel {
        return getFragmentScopeViewModel(BottleViewModel::class.java)
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