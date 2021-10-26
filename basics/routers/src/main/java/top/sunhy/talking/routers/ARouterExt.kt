package top.sunhy.talking.routers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import java.io.Serializable

/**
 * App整体跳转的方法
 */

fun Activity.navigation(path: String, bundle: Bundle? = null) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(this)
}

fun Activity.navigation(path: String, bundle: Bundle? = null, callback: NavigationCallback) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(this, callback)
}

fun Fragment.navigation(path: String, bundle: Bundle? = null) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(activity)
}

fun Fragment.navigation(path: String, bundle: Bundle? = null, callback: NavigationCallback) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(activity, callback)
}

fun Activity.getFragment(path: String, bundle: Bundle? = null): Fragment {
    return ARouter.getInstance().build(path).with(bundle).navigation() as Fragment
}

fun Fragment.getFragment(path: String, bundle: Bundle? = null): Fragment {
    return ARouter.getInstance().build(path).with(bundle).navigation() as Fragment
}

fun getFragmentByARouter(path: String, bundle: Bundle? = null): Fragment {
    return ARouter.getInstance().build(path).with(bundle).navigation() as Fragment
}

/**
 * 传递的 object 对象 需要实现 Serializable 接口
 */
fun navigationByARouterWithSerializable(
    context: Context,
    path: String,
    bundle: Bundle? = null,
    vararg sobj: Pair<String, *>
) {
    val sobjs: Map<String, *> = mutableMapOf(*sobj)
    ARouter.getInstance().build(path)
        .apply {
            bundle?.let { with(it) }
            if (!sobjs.isNullOrEmpty()){
                sobjs.forEach {
                    if (it.value is Serializable){
                        withSerializable(it.key, it.value as Serializable)
                    }
                }
            }
        }
        .navigation(context)
}

/**
 * 传递的 object 对象 不能实现 Serializable 接口
 */
fun navigationByARouter(
    context: Context,
    path: String,
    bundle: Bundle? = null,
    vararg obj: Pair<String, *>,
    isPush: Boolean = false
) {
    val objs: Map<String, *> = mutableMapOf(*obj)
    ARouter.getInstance().build(path)
        .apply {
            bundle?.let { with(it) }
            if (!objs.isNullOrEmpty()){
                objs.forEach {
                    withObject(it.key, it.value)
                }
            }
            if (isPush){
                withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        .navigation(context)
}

fun navigationByARouter(
    context: Context,
    path: String,
    bundle: Bundle? = null,
    vararg obj: Pair<String, *>,
    callback: NavigationCallback
) {
    val objs: Map<String, *> = mutableMapOf(*obj)
    ARouter.getInstance()
        .build(path).apply {
            bundle?.let { with(it) }
            objs.forEach {
                withObject(it.key, it.value)
            }
        }
        .navigation(context, callback)
}