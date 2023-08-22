package com.sopt.geonppang.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor():ViewModel() {
    val loginEmail = MutableLiveData("")
    val loginPassword = MutableLiveData("")

    //TODO 이메일과 비밀번호 입력이 완료 되면 로그인 버튼 활성화 되도록


}