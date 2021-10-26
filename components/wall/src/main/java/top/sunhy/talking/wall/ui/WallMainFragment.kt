package top.sunhy.talking.wall.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.fragment.BaseFragment
import top.sunhy.talking.routers.WallRouter
import top.sunhy.talking.wall.R
import top.sunhy.talking.wall.WallViewModel
import top.sunhy.talking.wall.databinding.WallFragmentMainBinding

/**
 * Home组件 入口
 */
@Route(path = WallRouter.FRAGMENT_MAIN)
class WallMainFragment: BaseFragment<WallFragmentMainBinding, WallViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.wall_fragment_main
    }

    override fun initViewModel(): WallViewModel {
        return getFragmentScopeViewModel(WallViewModel::class.java)
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