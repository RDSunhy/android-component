package top.sunhy.common.core.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.cancel
import top.sunhy.base.activity.AbsBaseActivity
import top.sunhy.base.application.BaseApplication
import top.sunhy.common.R
import top.sunhy.common.core.vm.BaseViewModel
import top.sunhy.common.loadsir.EmptyCallback
import top.sunhy.common.loadsir.ErrorCallback
import top.sunhy.common.loadsir.LoadingCallback
import top.sunhy.ktx.dp


abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AbsBaseActivity<B, VM>(){

    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM

    private lateinit var mActivityProvider: ViewModelProvider
    private lateinit var mApplicationProvider: ViewModelProvider

    protected var mLoadService: LoadService<*>? = null

    protected var mPopupLoading: LoadingPopupView? = null


    private var mOnPageStart = System.currentTimeMillis()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(object : ContextWrapper(newBase) {
            override fun getSystemService(name: String): Any? {
                // 解决 AudioManager 造成的内存泄漏
                return if (AUDIO_SERVICE == name) {
                    applicationContext.getSystemService(name)
                } else super.getSystemService(name)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setStatusBarTransparent(this)
        setStatusBarText()
        init()
        initParams(savedInstanceState)
        initView()
        initCommonView()
        initData()
        initListener()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        mOnPageStart = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
        cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    protected open fun init() {
        initDataBinding()
        mViewModel = initViewModel()
        mViewModel.application = BaseApplication.instance()
        lifecycle.addObserver(mViewModel)
    }

    open fun initCommonView() {

    }

    protected fun setLoadsir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetry() }
    }

    protected open fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
    }

    /**
     * Activity作用域内共享vm
     */
    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        mActivityProvider = ViewModelProvider(this)
        return mActivityProvider.get(modelClass)
    }

    /**
     * 全局共享vm
     */
    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (!this::mApplicationProvider.isInitialized) {
            mApplicationProvider = ViewModelProvider(
                this.applicationContext as BaseApplication,
                getAppFactory(this)
            )
        }
        return mApplicationProvider.get(modelClass)
    }

    protected fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application: Application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    protected fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //    window.insetsController?.setSystemBarsAppearance(
            //        0,
            //        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            //    )
            //}else{
            val option = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = option
            //}
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.parseColor("#00000000")
            } else {
                window.statusBarColor = Color.parseColor("#20000000")
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置状态栏字体颜色 是否为黑色
     * @param isBlack  true：字体为黑色
     */
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    fun setStatusBarText(isBlack: Boolean = true) {
        val window = this.window
        if (isBlack) {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                window.insetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }else{
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }*/
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            setStatusBarTransparent(this)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            setStatusBarTransparent(this)
        }
    }

    /**
     * 隐藏状态栏
     * @param isHidden true：隐藏
     */
    fun setStatusBarHidden(isHidden: Boolean = true) {
        val window = this.window
        if (isHidden) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun onRetry() {
        showLoading()
    }

    protected fun showLoadingDialog() {
        showLoadingDialog(null)
    }

    override fun showLoadingDialog(msg: String?) {
        mPopupLoading = XPopup.Builder(this)
            .isDestroyOnDismiss(true)
            .asLoading(msg ?: "正在加载中...", R.layout.common_popup_loading)
            .show() as LoadingPopupView?
    }

    override fun dismissLoadingDialog() {
        mPopupLoading?.smartDismiss()
    }

    /**
     * 修改 loading 弹窗的显示文本
     */
    protected fun setLoadingDialogText(text: String) {
        if (text.isNullOrEmpty()) {
            return
        }
        mPopupLoading?.setTitle(text)
    }

    override fun showLoading() {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun showContent() {
        mLoadService?.showSuccess()
    }

    override fun showEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    override fun showError() {
        mLoadService?.showCallback(ErrorCallback::class.java)
    }

    /**
     * RecyclerView 初始化方法
     */
    fun initRvSetting(
        view: RecyclerView,
        adapter: MultiTypeAdapter,
        lm: RecyclerView.LayoutManager? = null
    ) {
        view.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        if (lm == null) {
            view.layoutManager = LinearLayoutManager(this)
        } else {
            view.layoutManager = lm
        }
        view.adapter = adapter
    }

    /**
     * 添加悬浮view
     */
    protected fun addSnackBar(
        view: View?,
        width: Int = ScreenUtils.getScreenWidth(),
        height: Int = 80.dp,
        isBottom: Boolean = true,
        relMargin: Int = if (isBottom) 70.dp else 50.dp
    ) {
        if (view == null) {
            return
        }
        val rootView = window.decorView as ViewGroup
        rootView.addView(view, FrameLayout.LayoutParams(width, height).apply {
            if (isBottom) {
                gravity = Gravity.CENTER or Gravity.BOTTOM
                bottomMargin = relMargin
            } else {
                gravity = Gravity.CENTER or Gravity.TOP
                topMargin = relMargin
            }
        })
        AnimatorSet().apply {
            if (isBottom) {
                play(
                    ObjectAnimator.ofFloat(view, "translationY", 50f.dp, 0f)
                ).with(
                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f)
                )
            } else {
                play(
                    ObjectAnimator.ofFloat(view, "translationY", (-50f).dp, 0f)
                ).with(
                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f)
                )
            }

            duration = 1000
        }.start()
    }

}