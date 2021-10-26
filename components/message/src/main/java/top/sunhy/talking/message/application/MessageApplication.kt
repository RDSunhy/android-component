package top.sunhy.talking.message.application

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import top.sunhy.base.application.BaseApplication

class MessageApplication : BaseApplication(){
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