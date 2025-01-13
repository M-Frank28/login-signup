import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms.api.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.loginUser(email, password)
            _loginState.value = result.fold(
                onSuccess = { LoginState.Success(it.token, it.user) },
                onFailure = { LoginState.Error(it.message ?: "Error occurred") }
            )
        }
    }
}