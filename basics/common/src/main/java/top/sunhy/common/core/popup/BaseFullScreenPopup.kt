package top.sunhy.common.core.popup

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.impl.FullScreenPopupView
import com.lxj.xpopup.impl.LoadingPopupView
import top.sunhy.base.adapter.BaseMultiAdapter
import top.sunhy.base.application.BaseApplication
import top.sunhy.common.R
import top.sunhy.common.core.vm.BaseViewModel
import top.sunhy.common.loadsir.EmptyCallback
import top.sunhy.common.loadsir.ErrorCallback
import top.sunhy.common.loadsir.LoadingCallback

abstract class BaseFullScreenPopup<B : ViewDataBinding, VM : BaseViewModel>(activity: AppCompatActivity) :
    FullScreenPopupView(activity), IBasePopup {

    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM

    protected var mActivity = activity

    /**
     * 预置 recyclerview 相关内容
     */
    protected var mBefore = ""
    protected var mDatas = mutableListOf<Any>()
    protected var mAdapter = BaseMultiAdapter(mDatas)

    /**
     * 状态布局
     */
    protected var mLoadService: LoadService<*>? = null

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun registViewBinder()
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun initViewModel(): VM
    protected abstract fun initObserver()

    //加载中弹窗
    protected var mPopupLoading: LoadingPopupView? = null

    override fun initPopupContent() {
        super.initPopupContent()
        popupInfo.popupAnimation = PopupAnimation.ScaleAlphaFromCenter
        popupInfo.isDestroyOnDismiss = true
        mBinding = DataBindingUtil.bind(popupImplView)
            ?: throw NullPointerException("XmxCenterPopup mBinding is Null!")
        mViewModel = initViewModel()
        mViewModel.application = BaseApplication.instance()
        lifecycle.addObserver(mViewModel)
        initView()
        initCommonView()
        initListener()
        initData()
        initObserver()
    }

    protected fun setLoadsir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetry() }
    }

    override fun getImplLayoutId(): Int {
        return getLayoutId()
    }

    private fun initCommonView() {
        if (getRefreshLayout() != null) {
            getRefreshLayout()?.setOnRefreshListener(this)
            getRefreshLayout()?.setOnLoadMoreListener(this)
        }
        registViewBinder()
        getMainRecyclerview()?.overScrollMode = View.OVER_SCROLL_NEVER
        getMainRecyclerview()?.layoutManager = getLayoutManager()
        getMainRecyclerview()?.adapter = mAdapter
    }

    fun setBefore(time: Long){
        if (time != null){
            mBefore = time.toString()
        }
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(mActivity)
    }

    override fun onDismiss() {
        super.onDismiss()
    }

    override fun onShow() {
        super.onShow()
    }

    /**
     * 初始化一个新的 viewmodel
     * 不和 activity 或 fragment 共用！！！
     */
    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider.AndroidViewModelFactory(BaseApplication.instance())
            .create(modelClass)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRetry() {
        showLoading()
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

    protected fun showLoadingDialog(){
        showLoadingDialog(null)
    }

    protected fun showLoadingDialog(msg: String?) {
        mPopupLoading = XPopup.Builder(mActivity)
            .isDestroyOnDismiss(true)
            .asLoading(msg?:"正在加载中...", R.layout.common_popup_loading)
            .show() as LoadingPopupView?
    }

    /**
     * 修改 loading 弹窗的显示文本
     */
    protected fun setLoadingDialogText(text: String){
        if (text.isNullOrEmpty()){
            return
        }
        mPopupLoading?.setTitle(text)
    }

    protected fun dismissLoadingDialog() {
        mPopupLoading?.dismiss()
    }

}