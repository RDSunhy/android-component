package top.sunhy.common.core.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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
import com.lxj.xpopup.core.BasePopupView
import top.sunhy.base.application.BaseApplication
import top.sunhy.base.fragment.AbsBaseFragment
import top.sunhy.common.R
import top.sunhy.common.core.vm.BaseViewModel
import top.sunhy.common.loadsir.EmptyCallback
import top.sunhy.common.loadsir.ErrorCallback
import top.sunhy.common.loadsir.LoadingCallback
import top.sunhy.ktx.dp

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : AbsBaseFragment<B, VM>() {

    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM
    protected lateinit var mActivity: AppCompatActivity
    protected lateinit var mContext: Context

    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    protected var mLoadService: LoadService<*>? = null

    protected var mPopupLoading: BasePopupView? = null

    /**
     * AndroidX 懒加载
     */
    private var isLoaded = false

    private var mOnPageStart = System.currentTimeMillis()

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity as AppCompatActivity
        mContext = mActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        initParams(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.lifecycleOwner = this
        mViewModel = initViewModel()
        mViewModel.application = BaseApplication.instance()
        lifecycle.addObserver(mViewModel)
        initLoadSir()
        if (mLoadService != null) {
            var parentView: ViewGroup? = mLoadService?.loadLayout?.parent as ViewGroup?
            if (parentView != null) {
                parentView.endViewTransition(mLoadService?.loadLayout)
                parentView.removeView(mLoadService?.loadLayout)
            }
            return mLoadService?.loadLayout
        }
        var parentView: ViewGroup? = mBinding.root.parent as ViewGroup?
        if (parentView != null) {
            parentView.endViewTransition(mBinding.root)
            parentView.removeView(mBinding.root)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCommonView()
        initListener()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            mOnPageStart = System.currentTimeMillis()
            firstLoad()
            isLoaded = true
            return
        }
        if (isLoaded && !isHidden) {
            mOnPageStart = System.currentTimeMillis()
            lazyLoad()
        }
    }

    override fun onPause() {
        super.onPause()
        interrupt()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

    open fun initCommonView() {

    }

    protected fun setLoadsir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetry() }
    }

    /**
     * Fragment作用域内共享vm
     */
    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    /**
     * Activity作用域内共享vm
     */
    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    /**
     * 全局共享vm
     */
    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(
                BaseApplication.instance(),
                getAppFactory(mActivity)
            )
        }
        return mApplicationProvider!!.get(modelClass)
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        checkActivity(this)
        val application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    private fun checkActivity(fragment: Fragment) {
        if (fragment.activity == null) {
            throw java.lang.IllegalStateException("Can't create ViewModelProvider for detached fragment")
        }
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    protected open fun toggleSoftInput() {
        val imm = mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onRetry() {
        showLoading()
    }

    protected fun showLoadingDialog() {
        showLoadingDialog(null)
    }

    override fun showLoadingDialog(msg: String?) {
        mPopupLoading = XPopup.Builder(context)
            .asLoading(msg ?: "正在加载中...", R.layout.common_popup_loading)
            .show()
    }

    override fun dismissLoadingDialog() {
        mPopupLoading?.dismiss()
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

    fun initRvSetting(
        view: RecyclerView,
        adapter: MultiTypeAdapter,
        lm: RecyclerView.LayoutManager? = null
    ) {
        view.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        if (lm == null) {
            view.layoutManager = LinearLayoutManager(requireContext())
        } else {
            view.layoutManager = lm
        }
        view.adapter = adapter
    }

    protected fun addSnackBar(
        view: View?,
        width: Int = ScreenUtils.getScreenWidth() - 30.dp,
        isBottom: Boolean = true,
        relMargin: Int = if (isBottom) 70.dp else 50.dp
    ) {
        if (view == null) {
            return
        }
        val rootView = mActivity.window.decorView as ViewGroup
        rootView.addView(
            view,
            FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
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

    protected fun removeSnackBar(view: View?) {
        if (view == null) {
            return
        }
        val rootView = mActivity.window.decorView as ViewGroup
        rootView.removeView(view)
    }

}