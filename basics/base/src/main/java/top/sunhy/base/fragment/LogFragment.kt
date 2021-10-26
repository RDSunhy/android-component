package top.sunhy.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils

open class LogFragment : Fragment() {

    private var TAG = "#Fragment#${javaClass.simpleName}"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtils.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "onCreate: ")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d(TAG, "onViewCreated: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.d(TAG, "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d(TAG, "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.d(TAG, "onDestroyView: ")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.d(TAG, "onDetach: ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.d(TAG, "onHiddenChanged:hidden-->$hidden")
    }

}