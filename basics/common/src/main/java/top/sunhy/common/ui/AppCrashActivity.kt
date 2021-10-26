package top.sunhy.common.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import top.sunhy.common.R

class AppCrashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_activity_error)
        setStatusBarTransparent(this)
        setStatusBarText(this, true)

        /*
        CustomActivityOnCrash.getStackTraceFromIntent(intent)
        CustomActivityOnCrash.getActivityLogFromIntent(intent)
        CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
        CustomActivityOnCrash.getConfigFromIntent(intent)
        */

        val errorDetailsText = findViewById<TextView>(R.id.error_details)
        errorDetailsText.text = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
        val restartButton = findViewById<Button>(R.id.restart_button)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (config == null) {
            finish()
            return
        }
        if (config.isShowRestartButton && config.restartActivityClass != null) {
            restartButton.text = "重新启动App"
            restartButton.setOnClickListener {
                CustomActivityOnCrash.restartApplication(
                    this,
                    config
                )
            }
        } else {
            restartButton.setOnClickListener {
                CustomActivityOnCrash.closeApplication(
                    this,
                    config
                )
            }
        }
    }

    private fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.parseColor("#00000000")
            } else {
                window.statusBarColor = Color.parseColor("#20000000")
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun setStatusBarText(activity: Activity, isBlack: Boolean) {
        val window = activity.window
        if (isBlack) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            setStatusBarTransparent(activity)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            setStatusBarTransparent(activity)
        }
    }
}