import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms.repositories.SignupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val token: String, val user: Int) : SignupState()
    data class Error(val message: String) : SignupState()
}

class SignupViewModel(private val repository: SignupRepository) : ViewModel() {
    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState

    fun signupUser(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading
            val result = repository.signupUser(email, password)
            _signupState.value = result.fold(
                onSuccess = { SignupState.Success(it.message, it.status) },
                onFailure = {SignupState.Error(it.message ?: "Error occurred") }
            )
        }
    }
}