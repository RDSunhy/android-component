package top.sunhy.common.loadsir

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.kingja.loadsir.callback.Callback
import top.sunhy.common.R
import java.util.*

class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.common_layout_loadsir_loading
    }

    override fun onAttach(context: Context, view: View?) {
        super.onAttach(context, view)
        context.isRestricted
    }

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        return true
    }
}