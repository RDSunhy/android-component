package top.sunhy.ktx

import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils

/**
 * 13位时间戳 转 友好型时间
 */
fun TextView.setTime(time: Long) {
    if (time == null) {
        this.setGone()
        return
    }
    this.setVisible()
    var millis = time
    val now = System.currentTimeMillis()
    val span: Long = now - millis
    if (span < 0) {
        this.text = "刚刚"
    } else {
        this.text = TimeUtils.getFriendlyTimeSpanByNow(millis)
    }
}