package top.sunhy.network.extension

import androidx.lifecycle.MutableLiveData
import top.sunhy.network.Resource

/**
 * 包装 LiveData
 */
class ResLiveData<T> : MutableLiveData<Resource<T>>()

/**
 * 包装 list 类型 LiveData
 */
class ResListLiveData<T>:MutableLiveData<Resource<MutableList<T>>>()