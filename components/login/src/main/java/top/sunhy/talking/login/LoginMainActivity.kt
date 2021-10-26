package top.sunhy.talking.login

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.common.utils.SpUtils
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.network.extension.deal
import top.sunhy.talking.login.databinding.LoginActivityMainBinding
import top.sunhy.talking.routers.LoginRouter
import top.sunhy.talking.routers.getFragment

/**
 * Login组件 独立允许主 Activity
 */
@Route(path = LoginRouter.ACTIVITY_MAIN)
class LoginMainActivity : BaseActivity<LoginActivityMainBinding, LoginViewModel>() {

    private val mLoginFragment = getFragment(LoginRouter.FRAGMENT_MAIN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KeyboardUtils.fixAndroidBug5497(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity_main
    }

    override fun initViewModel(): LoginViewModel {
        return getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        window.navigationBarColor = Color.parseColor("#f5f5f5")
        loadFragments(R.id.flHost, 0, *mutableListOf(mLoginFragment).toTypedArray())
        showHideFragment(mLoginFragment)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {

    }

}