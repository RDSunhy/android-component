package top.sunhy.talking.mine.application

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import top.sunhy.base.application.BaseApplication

class MineApplication : BaseApplication(){
    override fun onModuleAppCreate(application: Application?) {
        if (isDebug()){
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(this)
    }

    override fun initModule(application: Application?) {

    }
}