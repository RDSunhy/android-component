package top.sunhy.talking.wall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.talking.routers.WallRouter
import top.sunhy.talking.routers.getFragment
import top.sunhy.talking.wall.databinding.WallActivityMainBinding

/**
 * Home组件 独立允许主 Activity
 */
@Route(path = WallRouter.ACTIVITY_MAIN)
class WallMainActivity : BaseActivity<WallActivityMainBinding, WallViewModel>() {

    private val mWallFragment = getFragment(WallRouter.FRAGMENT_MAIN)

    override fun getLayoutId(): Int {
        return R.layout.wall_activity_main
    }

    override fun initViewModel(): WallViewModel {
        return getActivityScopeViewModel(WallViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        loadFragments(R.id.flHost, 0, *mutableListOf(mWallFragment).toTypedArray())
        showHideFragment(mWallFragment)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

}