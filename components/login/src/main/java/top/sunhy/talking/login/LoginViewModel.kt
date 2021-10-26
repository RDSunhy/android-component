package top.sunhy.talking.login

import com.alibaba.fastjson.JSONObject
import top.sunhy.common.api.HttpUtils
import top.sunhy.common.extension.toRequestBody
import top.sunhy.common.utils.EncodeUtils
import top.sunhy.common.vm.CommonViewModel
import top.sunhy.network.extension.ResLiveData
import top.sunhy.talking.entity.DefaultResponse
import top.sunhy.talking.entity.LoginBean

class LoginViewModel: CommonViewModel() {

    var mLogin = ResLiveData<LoginBean>()
    var mRegist = ResLiveData<DefaultResponse>()
    var mReset = ResLiveData<LoginBean>()

    fun loginByEmail(email: String, pwd: String){
        fetchResource {
            var json = JSONObject()
            json["email"] = email
            json["password"] = EncodeUtils.sha256(pwd)
            mLogin.postValue(commonRepo.execute(HttpUtils.loginApi.loginByEmail(json.toRequestBody())))
        }
    }

    fun registByEmail(email: String, username: String, pwd: String, code: String){
        fetchResource {
            var json = JSONObject()
            json["email"] = email
            json["username"] = username
            json["password"] = EncodeUtils.sha256(pwd)
            json["code"] = code
            mRegist.postValue(commonRepo.execute(HttpUtils.loginApi.registByEmail(json.toRequestBody())))
        }
    }
}