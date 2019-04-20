package com.example.project1.api


import com.example.project1.model.DefaultResponse
import com.example.project1.model.Restaurant
import com.example.project1.model.User
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {


    //@FormUrlEncoded
    @POST("auth/signUp")
    fun createUser(@Body user:HashMap<String, Any>
    ): Call<DefaultResponse>

    @GET("rest/restList")
    fun getRestaurant():Call<ArrayList<Restaurant>>

}