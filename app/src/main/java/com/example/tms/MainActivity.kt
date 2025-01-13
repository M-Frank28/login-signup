package com.example.tms

import RetrofitInstance
import SignupScreen
import SignupViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tms.api.ApiService
import com.example.tms.api.SignupApiService
import com.example.tms.api.TokenManager
import com.example.tms.repositories.SignupRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokenManager = TokenManager(this)
        val signapiService = RetrofitInstance.retrofit.create(SignupApiService::class.java)
//        val loginRepository = LoginRepository(apiService)
//        val loginViewModel = LoginViewModel(loginRepository)
        val signupRepository= SignupRepository(signapiService)
        val signupViewModel= SignupViewModel(signupRepository)


        setContent {
//            LoginScreen(viewModel = loginViewModel, tokenManager = tokenManager)
            SignupScreen(viewModel = signupViewModel, tokenManager = tokenManager)

        }
    }
}


