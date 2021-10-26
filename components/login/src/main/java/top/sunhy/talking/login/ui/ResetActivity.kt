package top.sunhy.talking.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.talking.login.LoginViewModel
import top.sunhy.talking.login.R
import top.sunhy.talking.login.databinding.LoginActivityResetBinding
import top.sunhy.talking.routers.LoginRouter

@Route(path = LoginRouter.ACTIVITY_RESET)
class ResetActivity: BaseActivity<LoginActivityResetBinding, LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KeyboardUtils.fixAndroidBug5497(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity_reset
    }

    override fun initViewModel(): LoginViewModel {
        return getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        mBinding.ivBack.setOnClickListener { finish() }
    }

    override fun initObserver() {

    }
}