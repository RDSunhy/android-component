package top.sunhy.talking.mine

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.talking.mine.databinding.MineActivityMainBinding
import top.sunhy.talking.routers.MineRouter
import top.sunhy.talking.routers.getFragment

/**
 * Mine组件 独立允许主 Activity
 */
@Route(path = MineRouter.ACTIVITY_MAIN)
class MineMainActivity : BaseActivity<MineActivityMainBinding, MineViewModel>() {

    private val mMineFragment = getFragment(MineRouter.FRAGMENT_MAIN)

    override fun getLayoutId(): Int {
        return R.layout.mine_activity_main
    }

    override fun initViewModel(): MineViewModel {
        return getActivityScopeViewModel(MineViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        loadFragments(R.id.flHost, 0, *mutableListOf(mMineFragment).toTypedArray())
        showHideFragment(mMineFragment)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

}