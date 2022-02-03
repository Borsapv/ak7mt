package cz.borsma.zpravodajstvi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//create an url
object Api {

    private val BASE_URL = "https://newsapi.org/v2/"
    //public const val API_KEY = "39679e433998453c834d818ee9a358fc"
    public const val API_KEY = "3b0a25577b1d4356a6eccb4747b5beb8"

    //transform json data into kotlin data
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val logging = HttpLoggingInterceptor()


    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{chain->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )

        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}