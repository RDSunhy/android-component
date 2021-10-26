package top.sunhy.base.vm

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import top.sunhy.base.core.IViewModel
import top.sunhy.base.core.ActivityLifecycObserver

abstract class AbsViewModel : ViewModel(), ActivityLifecycObserver, IViewModel {

    lateinit var application: Application
    private lateinit var lifcycleOwner: LifecycleOwner

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        viewModelScope.cancel()
        onCleared()
    }
}
