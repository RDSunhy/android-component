package top.sunhy.base.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * App前后台切换监听
 */
object AppStateTracker {

    interface AppStateChangeListener {
        /**
         * app 从后台回到前台
         */
        fun appTurnIntoForeground()

        /**
         * app 从前台切换到后台
         */
        fun appTurnIntoBackGround()
    }

    const val STATE_FOREGROUND = 0

    const val STATE_BACKGROUND = 1

    private var currentState = 0

    fun getCurrentState(): Int {
        return currentState
    }

    fun init(app: Application, listener: AppStateChangeListener? = null) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            private var resumeActivityCount = 0

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                if (resumeActivityCount == 0) {
                    currentState = STATE_FOREGROUND
                    listener?.appTurnIntoForeground()
                }
                resumeActivityCount++
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                resumeActivityCount--
                if (resumeActivityCount == 0) {
                    currentState = STATE_BACKGROUND
                    listener?.appTurnIntoBackGround()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }
}