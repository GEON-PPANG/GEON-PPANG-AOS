package com.sopt.geonppang.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val password_check = MutableLiveData("")
    val nickname = MutableLiveData("")

    val isValidEmail: LiveData<Boolean> = email.map { email ->
        email.matches(Regex(EMAIL_PATTERN))
    }

    val isValidNickname: LiveData<Boolean> = nickname.map { nickname ->
        nickname.matches(Regex(NICKNAME_PATTERN))
    }

    val isValidPassword: LiveData<Boolean> = password.map { password ->
        password.matches(Regex(PASSWORD_PATTERN))
    }

    val isValidPasswordCheck: LiveData<Boolean> = password_check.map { password_check ->
        password_check.matches(Regex(PASSWORD_PATTERN))
    }

    val doubleCheckEmail = MediatorLiveData<Boolean>().apply {
        //Todo 중복 확인 구현 예정
    }

    val doubleCheckNickname = MediatorLiveData<Boolean>().apply {
        //Todo 닉네임 중복 확인 구현 예정
    }

    /*다음 버튼 활성화*/
    val completeEmail = MediatorLiveData<Boolean>().apply {
        addSource(email) { value = checkEmailCondition() }
    }

    /*다음 버튼 활성화*/
    val completePassword = MediatorLiveData<Boolean>().apply {
        addSource(password) { value = isPasswordSame() }
        addSource(password_check) { value = isPasswordSame() }
    }

    val completeNickname = MediatorLiveData<Boolean>().apply {
        addSource(nickname) { value = checkNicknameCondition() }
        /*다음 버튼 활성화*/
    }

    private fun isPasswordSame(): Boolean {
        return password.value.toString() == password_check.value.toString()
                && !password.value.isNullOrBlank()
                && !password_check.value.isNullOrBlank()
    }

    /*이메일 조건*/
    private fun checkEmailCondition(): Boolean {
        return isValidEmail.value == true
    }

    /*닉네임 조건ㅁㄴ*/
    private fun checkNicknameCondition(): Boolean {
        return isValidNickname.value == true
    }


    companion object {
        const val EMAIL_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        const val NICKNAME_PATTERN = "[가-힣]*[A-Za-z[0-9]]{2,10}\$"
        const val PASSWORD_PATTERN =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{6,12}\$"

    }


    /*fun uploadDummy() {
        viewModelScope.launch {
            dummyRepository.uploadDummy("name", "dummy")
                .onSuccess {
                }
                .onFailure {
                }
        }
    }*/
}
