package top.sunhy.talking.message

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.activity.BaseActivity
import top.sunhy.ktx.loadFragments
import top.sunhy.ktx.showHideFragment
import top.sunhy.talking.message.databinding.MessageActivityMainBinding
import top.sunhy.talking.routers.MessageRouter
import top.sunhy.talking.routers.getFragment

/**
 * Home组件 独立允许主 Activity
 */
@Route(path = MessageRouter.ACTIVITY_MAIN)
class MessageMainActivity : BaseActivity<MessageActivityMainBinding, MessageViewModel>() {

    private val mHomeFragment = getFragment(MessageRouter.FRAGMENT_MAIN)

    override fun getLayoutId(): Int {
        return R.layout.message_activity_main
    }

    override fun initViewModel(): MessageViewModel {
        return getActivityScopeViewModel(MessageViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        loadFragments(R.id.flHost, 0, *mutableListOf(mHomeFragment).toTypedArray())
        showHideFragment(mHomeFragment)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

}