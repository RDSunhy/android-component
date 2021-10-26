package top.sunhy.base.utils

import android.app.Activity
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

object ActivityManager {

    private val activityStack: Stack<Activity> = Stack()

    fun add(activity: Activity) {
        activityStack.add(activity)
    }

    fun remove(activity: Activity) {
        if (activityStack.contains(activity)) {
            activity.finish()
            activityStack.remove(activity)
        }
    }

    fun getTop(): Activity{
        return activityStack.lastElement()
    }

    fun clear() {
        val it = activityStack.iterator()
        while (it.hasNext()) {
            val activity = it.next()
            activity.finish()
        }
        activityStack.clear()
    }

    fun exit(context: Context) {
        clear()
        exitProcess(0)
    }
}