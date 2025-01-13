package com.example.tms.repositories

import com.example.tms.api.ApiService
import com.example.tms.api.SignupApiService
import com.example.tms.api.SignupRequest
import com.example.tms.api.SignupResponse

class SignupRepository(private val signapiService: SignupApiService) {

    suspend fun  signupUser(email: String, password: String):Result<SignupResponse>{
        return try {
            val response = signapiService.signupUser(SignupRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }


    }

}