package top.sunhy.common.extension

import android.app.Activity
import com.blankj.utilcode.util.ColorUtils

fun Activity.getResColor(resId: Int): Int{
    return ColorUtils.getColor(resId)
}