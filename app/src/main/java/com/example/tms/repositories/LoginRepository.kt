import com.example.tms.api.ApiService
import com.example.tms.api.LoginRequest
import com.example.tms.api.LoginResponse

class LoginRepository(private val apiService: ApiService) {
    suspend fun loginUser(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.loginUser(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
