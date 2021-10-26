package top.sunhy.ktx

import android.graphics.Paint
import android.graphics.Rect
import android.view.View

fun View.getLocation(): IntArray{
    var location = IntArray(2)
    this.getLocationOnScreen(location)
    return location
}

fun View.setGone(){
    this.visibility = View.GONE
}

fun View.setVisible(){
    this.visibility = View.VISIBLE
}

fun View.setInvisible(){
    this.visibility = View.INVISIBLE
}

/**
 * use for get textview base line -- X
 */
inline fun baselineX(paint: Paint, text: String, width: Float): Float {
    val rectBounds = Rect()
    paint.getTextBounds(text, 0, text.length, rectBounds)
    return width - rectBounds.width() / 2
}

/**
 * use for get textview base line -- Y
 */
inline fun baselineY(paint: Paint, height: Float): Float {
    val fontMetricsInt = paint.fontMetricsInt
    val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
    return height + dy
}