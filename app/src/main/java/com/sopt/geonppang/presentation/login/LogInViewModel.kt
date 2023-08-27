package com.sopt.geonppang.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.sopt.geonppang.presentation.signup.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    val loginEmail = MutableLiveData("")
    val loginPassword = MutableLiveData("")

    val isLoginEmail: LiveData<Boolean> = loginEmail.map { login_email ->
        login_email.matches(Regex(SignUpViewModel.EMAIL_PATTERN))
    }
    val isLoginPassword: LiveData<Boolean> = loginPassword.map { login_password ->
        login_password.matches(Regex(SignUpViewModel.PASSWORD_PATTERN))
    }
}
