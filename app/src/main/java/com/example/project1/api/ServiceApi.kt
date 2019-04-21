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

    @POST("auth/signIn")
    fun logIn(@Body user:HashMap<String, Any>
    ): Call<DefaultResponse>

    @POST("rest/addImg")//convertir imagen con base64
    fun addImage():Call<DefaultResponse>

    @POST("rest/addRest")
    fun addRest():Call<Restaurant>

    @GET("rest/restList")
    fun getRestaurant():Call<ArrayList<Restaurant>>

}