package top.sunhy.talking.message.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import top.sunhy.common.core.fragment.BaseFragment
import top.sunhy.talking.message.MessageViewModel
import top.sunhy.talking.message.R
import top.sunhy.talking.message.databinding.MessageFragmentMainBinding
import top.sunhy.talking.routers.MessageRouter

/**
 * Home组件 入口
 */
@Route(path = MessageRouter.FRAGMENT_MAIN)
class MessageMainFragment: BaseFragment<MessageFragmentMainBinding, MessageViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.message_fragment_main
    }

    override fun initViewModel(): MessageViewModel {
        return getFragmentScopeViewModel(MessageViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
    }

    override fun lazyLoad() {
    }

    override fun interrupt() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }
}