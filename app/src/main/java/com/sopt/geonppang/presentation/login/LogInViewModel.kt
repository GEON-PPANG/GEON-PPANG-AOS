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
}
