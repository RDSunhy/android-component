package top.sunhy.common.loadsir

import com.kingja.loadsir.callback.Callback
import top.sunhy.common.R

class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_layout_loadsir_empty
    }
}