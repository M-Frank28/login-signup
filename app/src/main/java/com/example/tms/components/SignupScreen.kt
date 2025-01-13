import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tms.api.TokenManager
import kotlinx.coroutines.launch


@Composable
fun SignupScreen(viewModel:SignupViewModel,tokenManager: TokenManager){
    val signupState by viewModel.signupState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signupSuccess by remember { mutableStateOf(false) }

    // Handling the successful sign up and saving the token as a side effect

    LaunchedEffect(signupState ) {

        if (signupState is SignupState.Success ){

            val token = (signupState as SignupState.Success).token
            coroutineScope.launch{
                tokenManager.saveToken(token)
                signupSuccess= true // Set a flag to indicate sign up success
            }
    }
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 50.dp

        ),
        modifier = Modifier
            .size(width = 440.dp, height = 500.dp)
            .padding(top = 50.dp, start = 50.dp,end=50.dp)

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Use the content padding provided by Scaffold
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
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
            Button(onClick = { viewModel.signupUser(email, password) }){
                Text("Signup")
            }
            Spacer(modifier = Modifier.height(16.dp))
            when(signupState){
                is SignupState.Loading-> CircularProgressIndicator()
                is SignupState.Success ->{

                    if(signupSuccess){
                        ("Sign up Successfull")
                    }
                }
                is SignupState.Error-> Text("Error: ${( signupState as SignupState.Error).message}")
                else -> {}
            }

        }
    }

}
