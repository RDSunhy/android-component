package top.sunhy.common

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import top.sunhy.base.application.BaseApplication
import top.sunhy.base.utils.AppStateTracker
import top.sunhy.common.ui.AppCrashActivity
import top.sunhy.common.ui.splash.SplashActivity
import top.sunhy.image.core.ImageOptions
import top.sunhy.talking.event.AppStateEvent

open class CommonApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        //App 前后台切换监听 对全局发送消息
        AppStateTracker.init(this, object : AppStateTracker.AppStateChangeListener {
            override fun appTurnIntoForeground() {
                LogUtils.e("AppStateTracker", "App 切换到前台")
                LiveEventBus.get(AppStateEvent::class.java)
                    .post(AppStateEvent(AppStateEvent.FOREGROUND))
            }

            override fun appTurnIntoBackGround() {
                LogUtils.e("AppStateTracker", "App 切换到后台")
                LiveEventBus.get(AppStateEvent::class.java)
                    .post(AppStateEvent(AppStateEvent.BACKGROUND))
            }
        })

        CaocConfig.Builder.create()
            //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，启动错误页面，
            //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误
            //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，静默关闭
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true)
            .showErrorDetails(true)  //错误详情按钮
            .showRestartButton(true) //重启按钮
            .trackActivities(true) //错误页面中显示错误详细信息
            .minTimeBetweenCrashesMs(1000) //崩溃之间的最短时间
            .errorDrawable(R.mipmap.img_logo) //崩溃页面显示的图标
            .restartActivity(SplashActivity::class.java) //重新启动后的页面
            .errorActivity(AppCrashActivity::class.java) //程序崩溃后显示的页面
            .apply()

        ImageOptions.DrawableOptions.setDefault {
            placeHolderResId = R.color.placeholder
            errorResId = R.color.placeholder
        }
    }

    override fun onModuleAppCreate(application: Application?) {

    }

    override fun initModule(application: Application?) {

    }
}