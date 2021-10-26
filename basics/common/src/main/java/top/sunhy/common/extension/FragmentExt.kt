package top.sunhy.common.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils

fun Fragment.getResColor(resId: Int): Int{
    return ColorUtils.getColor(resId)
}