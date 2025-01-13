import com.example.tms.api.ApiConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // Scalars Converter
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
