package top.sunhy.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import top.sunhy.base.utils.ActivityManager

open class LogActivity : AppCompatActivity() {

    private var TAG = "#Activity#${javaClass.simpleName}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.add(this)
        LogUtils.d(TAG, "onCreate: ActivityManager Added")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume: ")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "onRestart: ")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.remove(this)
        LogUtils.d(TAG, "onDestroy: ActivityManager Removed")
    }
}