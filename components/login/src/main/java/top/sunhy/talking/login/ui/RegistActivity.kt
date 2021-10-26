package top.sunhy.talking.login.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import top.sunhy.common.config.Constant
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.common.extension.getResColor
import top.sunhy.common.utils.SpUtils
import top.sunhy.network.extension.deal
import top.sunhy.talking.login.LoginViewModel
import top.sunhy.talking.login.R
import top.sunhy.talking.login.databinding.LoginActivityRegistBinding
import top.sunhy.talking.routers.LoginRouter

@Route(path = LoginRouter.ACTIVITY_REGIST)
class RegistActivity : BaseActivity<LoginActivityRegistBinding, LoginViewModel>() {

    private var mTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KeyboardUtils.fixAndroidBug5497(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity_regist
    }

    override fun initViewModel(): LoginViewModel {
        return getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        mBinding.ivBack.setOnClickListener { finish() }

        mBinding.tvSend.setOnClickListener {
            if (!RegexUtils.isEmail(mBinding.etEmail.text)) {
                ToastUtils.showShort("邮箱格式不正确")
                return@setOnClickListener
            }
            showLoadingDialog("正在发送中...")
            mViewModel.sendEmailCode(Constant.CODE_TYPE_REGIST, mBinding.etEmail.text.toString())
        }

        mBinding.tvRegist.setOnClickListener {
            if (checkInput()) {
                mViewModel.registByEmail(
                    mBinding.etEmail.text.toString(),
                    mBinding.etNickName.text.toString(),
                    mBinding.etPassword.text.toString(),
                    mBinding.etCode.text.toString()
                )
                showLoadingDialog("正在注册中...")
            }
        }
    }

    override fun initObserver() {
        mViewModel.mSendCode.observe(this, Observer { res ->
            dismissLoadingDialog()
            res.deal {
                mBinding.tvSend.isEnabled = false
                mBinding.tvSend.setBackgroundResource(R.drawable.bg_border_f2_round4_all)
                mBinding.tvSend.setTextColor(getResColor(R.color.gray))
                ToastUtils.showShort("发送成功，请注意查收~")
                setTimers()
            }
        })

        mViewModel.mRegist.observe(this, Observer { res ->
            dismissLoadingDialog()
            res.deal {
                SpUtils.saveLastLoginEmail(mBinding.etEmail.text.toString())
                ToastUtils.showShort("注册成功~")
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer?.cancel()
        mTimer = null
    }

    private fun checkInput(): Boolean {
        if (!RegexUtils.isEmail(mBinding.etEmail.text)) {
            ToastUtils.showShort("邮箱格式不正确")
            return false
        }

        if (mBinding.etNickName.text.isNullOrEmpty()) {
            ToastUtils.showShort("昵称不能为空")
            return false
        }

        if (mBinding.etPassword.text.isNullOrEmpty()) {
            ToastUtils.showShort("密码不能为空")
            return false
        }

        if (mBinding.etCode.text.isNullOrEmpty()) {
            ToastUtils.showShort("验证码不能为空")
            return false
        }

        return true
    }

    private fun setTimers() {
        mTimer = object : CountDownTimer(30 * 1000, 1000) {
            override fun onTick(l: Long) {
                mBinding.tvSend.text = "${l / 1000}秒后可发送"
            }

            override fun onFinish() {
                mBinding.tvSend.setBackgroundResource(R.drawable.bg_border_primary_round4_all)
                mBinding.tvSend.text = "发送验证码"
                mBinding.tvSend.setTextColor(getResColor(R.color.colorPrimary))
                mBinding.tvSend.isEnabled = true
            }
        }
        mTimer?.start()
    }
}