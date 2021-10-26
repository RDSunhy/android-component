package top.sunhy.common.ui.splash

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import top.sunhy.common.R
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.common.databinding.CommonActivitySplashBinding
import top.sunhy.talking.routers.*

@Route(path = CommonRouter.ACTIVITY_SPLASH)
class SplashActivity : BaseActivity<CommonActivitySplashBinding, SplashViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.common_activity_splash
    }

    override fun initViewModel(): SplashViewModel {
        return getActivityScopeViewModel(SplashViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        enterApp()
    }

    private fun enterApp() {
        delayInit()
        var appPackageName = AppUtils.getAppPackageName()
        when (appPackageName) {
            "top.sunhy.talking.bottle" -> navigation(BottleRouter.ACTIVITY_MAIN)
            "top.sunhy.talking.wall" -> navigation(WallRouter.ACTIVITY_MAIN)
            "top.sunhy.talking.message" -> navigation(MessageRouter.ACTIVITY_MAIN)
            "top.sunhy.talking.mine" -> navigation(MineRouter.ACTIVITY_MAIN)
            "top.sunhy.talking.login" -> navigation(LoginRouter.ACTIVITY_MAIN)
            "top.sunhy.talking" -> navigation(CommonRouter.APP_MAIN)
        }
        finish()
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {

    }

    //sdk 延迟初始化
    private fun delayInit() {

    }
}