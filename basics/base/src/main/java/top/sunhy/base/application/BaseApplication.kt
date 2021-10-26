package top.sunhy.base.application

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Process
import android.webkit.WebView
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import top.sunhy.base.utils.AppStateTracker
import kotlin.properties.Delegates


abstract class BaseApplication : AbsApplication(), ViewModelStoreOwner {

    companion object {
        private var instance: BaseApplication by Delegates.notNull()

        fun instance() = instance

        /**
         * 相关配置
         * @see com.tttell.xmx.basic.Constant
         * @see com.tttell.xmx.basic.H5
         */
        fun isDebug(): Boolean {
            return instance().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }

        fun getEnvironment(): String {
            return if (isDebug()) {
                ""
            } else {
                ""
            }
        }
    }

    private val mAppViewModelStore: ViewModelStore by lazy { ViewModelStore() }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    open fun registerActivityLifecycleCallback(callbacks: ActivityLifecycleCallbacks?) {
        registerActivityLifecycleCallbacks(callbacks)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //WebView bug fix
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName()
            if (packageName != processName) {
                WebView.setDataDirectorySuffix(packageName)
            }
        }

        //日志打印开关
        LogUtils.getConfig().isLogSwitch = isDebug()

        //ARouter初始化
        if (isDebug()){
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(this)

        //LiveEventBus初始化
        LiveEventBus
            .config()
            .autoClear(true)
            .enableLogger(true)
            .lifecycleObserverAlwaysActive(false)
            .setContext(this)

        onModuleAppCreate(this)
        initModule(this)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * 获取进程名
     *
     * @param context
     * @return
     */
    open fun getCurrentProcessName(context: Context): String? {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return null
    }
}