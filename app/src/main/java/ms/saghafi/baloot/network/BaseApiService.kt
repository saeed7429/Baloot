package ms.saghafi.baloot.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ms.saghafi.baloot.model.BaseResponseModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/v2/"

val moshi : Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


interface BaseApiService {

    @GET("everything")
    suspend fun getArticles(@Query("q") query: String,@Query("apiKey") apiKey: String,
                       @Query("pageSize") pageSize: Long,@Query("page") page: Long) : BaseResponseModel


}

object BaseApi {

    private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

    val retrofitService : BaseApiService by lazy { retrofit.create(BaseApiService::class.java)  }
}
