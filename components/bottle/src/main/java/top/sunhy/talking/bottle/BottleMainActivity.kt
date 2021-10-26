package top.sunhy.talking.bottle

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.talking.bottle.databinding.HomeActivityMainBinding
import top.sunhy.talking.routers.BottleRouter
import top.sunhy.talking.routers.getFragment

/**
 * Home组件 独立允许主 Activity
 */
@Route(path = BottleRouter.ACTIVITY_MAIN)
class BottleMainActivity : BaseActivity<HomeActivityMainBinding, BottleViewModel>() {

    private val mHomeFragment = getFragment(BottleRouter.FRAGMENT_MAIN)

    override fun getLayoutId(): Int {
        return R.layout.home_activity_main
    }

    override fun initViewModel(): BottleViewModel {
        return getActivityScopeViewModel(BottleViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        loadFragments(R.id.flHost, 0, *mutableListOf(mHomeFragment).toTypedArray())
        showHideFragment(mHomeFragment)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

}