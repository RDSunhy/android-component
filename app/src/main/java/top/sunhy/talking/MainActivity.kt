package top.sunhy.talking

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import top.sunhy.base.utils.ActivityManager
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.talking.databinding.ActivityMainBinding
import top.sunhy.talking.routers.*

@Route(path = CommonRouter.APP_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    //两次返回退出app标志
    private var isExit: Boolean = false

    private val mBottleFragment = getFragment(BottleRouter.FRAGMENT_MAIN)
    private val mWallFragment = getFragment(WallRouter.FRAGMENT_MAIN)
    private val mMessageFragment = getFragment(MessageRouter.FRAGMENT_MAIN)
    private val mMineFragment = getFragment(MineRouter.FRAGMENT_MAIN)

    private val fragments = mutableListOf(
        mBottleFragment,
        mWallFragment,
        mMessageFragment,
        mMineFragment
    )

    private var mMenusView: BottomNavigationMenuView? = null
    private var mBottleMenu: View? = null
    private var mWallMenu: View? = null
    private var mMessageMenu: View? = null
    private var mMineMenu: View? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return getActivityScopeViewModel(MainViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        initBottomView()

        loadFragments(R.id.flHost, 0, *fragments.toTypedArray())
    }

    override fun initData() {

    }

    override fun initListener() {
        mBinding.bottomView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottle -> {
                    if (mBinding.bottomView.selectedItemId == R.id.bottle) {
                        navigation(LoginRouter.ACTIVITY_MAIN)
                    } else {
                        showHideFragment(fragments[0])
                    }
                }
                R.id.wall -> {
                    if (mBinding.bottomView.selectedItemId == R.id.wall) {
                    } else {
                        showHideFragment(fragments[1])
                    }
                }
                R.id.message -> {
                    if (mBinding.bottomView.selectedItemId == R.id.message) {
                    } else {
                        showHideFragment(fragments[2])
                    }
                }

                R.id.mine -> {
                    if (mBinding.bottomView.selectedItemId == R.id.mine) {
                    } else {
                        showHideFragment(fragments[3])
                    }
                }
            }
            true
        }
    }

    override fun initObserver() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val handler = Handler();
            if (!isExit) {
                isExit = true
                ToastUtils.showShort("再按一次退出App")
                handler.postDelayed({ isExit = false }, 1000 * 2)
            } else {
                ActivityManager.exit(this)
            }
        }
        return false
    }

    private fun initBottomView() {
        mBinding.bottomView.itemIconTintList = null
        try {
            mMenusView = mBinding.bottomView.getChildAt(0) as BottomNavigationMenuView
            mMenusView?.let {
                for (i: Int in 0 until it.childCount) {
                    var tabView = it.getChildAt(i)
                    tabView.setOnLongClickListener { v ->
                        return@setOnLongClickListener true
                    }
                }

                mBottleMenu = it.getChildAt(0)
                mWallMenu = it.getChildAt(1)
                mMessageMenu = it.getChildAt(2)
                mMineMenu = it.getChildAt(3)
            }
        } catch (e: Exception) {
            LogUtils.e("mBinding.bottomView：${e.message.toString()}")
        }
    }
}