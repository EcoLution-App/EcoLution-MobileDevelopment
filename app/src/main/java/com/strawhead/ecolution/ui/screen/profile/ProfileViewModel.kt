package com.strawhead.ecolution.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.signin.SignInResult
import com.strawhead.ecolution.signin.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()


    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

}