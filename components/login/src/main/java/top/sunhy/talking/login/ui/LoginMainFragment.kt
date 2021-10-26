package top.sunhy.talking.login.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import top.sunhy.common.config.Constant
import top.sunhy.common.core.fragment.BaseFragment
import top.sunhy.common.utils.SpUtils
import top.sunhy.network.extension.deal
import top.sunhy.talking.login.LoginViewModel
import top.sunhy.talking.login.R
import top.sunhy.talking.login.databinding.LoginFragmentMainBinding
import top.sunhy.talking.routers.LoginRouter
import top.sunhy.talking.routers.navigation

/**
 * Mine组件 入口
 */
@Route(path = LoginRouter.FRAGMENT_MAIN)
class LoginMainFragment : BaseFragment<LoginFragmentMainBinding, LoginViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.login_fragment_main
    }

    override fun initViewModel(): LoginViewModel {
        return getFragmentScopeViewModel(LoginViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
        SpUtils.getInstance().getString(Constant.SP_KEY_LAST_EMAIL).let {
            if (!it.isNullOrEmpty()){
                mBinding.etEmail.setText(it)
            }
        }
    }

    override fun lazyLoad() {
        SpUtils.getInstance().getString(Constant.SP_KEY_LAST_EMAIL).let {
            if (!it.isNullOrEmpty()){
                mBinding.etEmail.setText(it)
            }
        }
    }

    override fun interrupt() {
    }

    override fun initListener() {
        mBinding.tvRegist.setOnClickListener { navigation(LoginRouter.ACTIVITY_REGIST) }
        mBinding.tvReset.setOnClickListener { navigation(LoginRouter.ACTIVITY_RESET) }
        mBinding.tvLogin.setOnClickListener {
            if (checkInput()){
                mViewModel.loginByEmail(
                    mBinding.etEmail.text.toString(),
                    mBinding.etPassword.text.toString()
                )
                showLoadingDialog("正在登陆中...")
            }
        }
    }

    override fun initObserver() {
        mViewModel.mLogin.observe(this, Observer { res ->
            dismissLoadingDialog()
            res.deal(error = {
                ToastUtils.showShort(it?.message?:"登录失败，请稍后重试")
            }, success = {
                SpUtils.saveToken(it)
                SpUtils.saveLastLoginEmail(mBinding.etEmail.text.toString())
                ToastUtils.showShort("登录成功~")
                mActivity.finish()
            })
        })
    }

    private fun checkInput(): Boolean{
        if (!RegexUtils.isEmail(mBinding.etEmail.text)){
            ToastUtils.showShort("邮箱格式不正确")
            return false
        }

        if (mBinding.etPassword.text.isNullOrEmpty()){
            ToastUtils.showShort("密码不能为空")
            return false
        }

        return true
    }
}