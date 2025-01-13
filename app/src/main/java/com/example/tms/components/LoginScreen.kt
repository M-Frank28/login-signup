package com.example.tms.components

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tms.api.TokenManager


@Composable
fun LoginScreen(viewModel: LoginViewModel, tokenManager: TokenManager) {
    val loginState by viewModel.loginState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginSuccess by remember { mutableStateOf(false) }

    // Handling the successful login and saving the token as a side effect
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val token = (loginState as LoginState.Success).token
            coroutineScope.launch {
                tokenManager.saveToken(token)
                loginSuccess = true // Set a flag to indicate login success
            }
        }
    }
 Card (
     elevation = CardDefaults.cardElevation(
         defaultElevation = 50.dp
     ),
     modifier = Modifier
         .size(width = 440.dp, height = 500.dp)
         .padding(top = 50.dp, start = 50.dp,end=50.dp)
 ) {
     Column(
         modifier = Modifier
             .fillMaxSize()
              // Use the content padding provided by Scaffold
             .padding(16.dp),
         verticalArrangement = Arrangement.Center
     ) {
         TextField(
             value = email,
             onValueChange = { email = it },
             label = { Text("Email") },
             modifier = Modifier.fillMaxWidth()
         )
         Spacer(modifier = Modifier.height(8.dp))
         TextField(
             value = password,
             onValueChange = { password = it },
             label = { Text("Password") },
             modifier = Modifier.fillMaxWidth()
         )
         Spacer(modifier = Modifier.height(16.dp))
         Button(onClick = { viewModel.loginUser(email, password) }) {
             Text("Login")
         }
         Spacer(modifier = Modifier.height(16.dp))
         when (loginState) {
             is LoginState.Loading -> CircularProgressIndicator()
             is LoginState.Success -> {
                 if (loginSuccess) {
                     ("Login Successful!")
                 }
             }
             is LoginState.Error -> Text("Error: ${(loginState as LoginState.Error).message}")
             else -> {}
         }
     }
 }
 }
