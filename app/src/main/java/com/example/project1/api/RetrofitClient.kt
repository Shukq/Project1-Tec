package com.example.project1.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    //private val AUTH = "Basic " + Base64.encodeToString("belalkhan:123456".toByteArray(),Base64.NO_WRAP)
    private const val BASE_URL = "https://rt6sa58oih.execute-api.us-east-1.amazonaws.com/dev/"
    private val okHTTPClient = OkHttpClient.Builder()
        .addInterceptor {chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                //.addHeader("Authorization","AUTH")
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ServiceApi by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHTTPClient)
            .build()
        retrofit.create(ServiceApi::class.java)
    }
}