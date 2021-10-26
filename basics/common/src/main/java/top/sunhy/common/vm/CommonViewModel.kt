package top.sunhy.common.vm

import com.alibaba.fastjson.JSONObject
import top.sunhy.common.api.HttpUtils
import top.sunhy.common.core.vm.BaseViewModel
import top.sunhy.common.extension.toRequestBody
import top.sunhy.common.repo.CommonRepo
import top.sunhy.common.utils.EncodeUtils
import top.sunhy.network.extension.ResLiveData
import top.sunhy.talking.entity.DefaultResponse

open class CommonViewModel: BaseViewModel() {
    var commonRepo = CommonRepo()

    var mSendCode = ResLiveData<DefaultResponse>()

    fun sendEmailCode(type: String, email: String){
        fetchResource {
            var json = JSONObject()
            json["type"] = type
            json["email"] = email
            mSendCode.postValue(commonRepo.execute(HttpUtils.loginApi.sendEmailCode(json.toRequestBody())))
        }
    }
}