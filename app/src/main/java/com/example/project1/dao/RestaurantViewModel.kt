package com.example.project1.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.project1.model.Restaurant
import javax.inject.Inject

class RestaurantViewModel(val context: Context) : ViewModel() {
    private var restaurant : LiveData<Restaurant>? = null
    private var restaurantRepo : RestaurantRepository ? = null

    @Inject
    fun RestaurantViewModel(){
        this.restaurantRepo = RestaurantRepository()
    }

    fun init() {
        this.restaurantRepo = RestaurantRepository()
        restaurantRepo?.getInstance(context)
        if(this.restaurant != null){
            return
        }
        restaurant = restaurantRepo!!.getRestaurant()
    }

    fun getRestaurant():LiveData<List<Restaurant>> {
        this.restaurantRepo = RestaurantRepository()
        restaurantRepo?.getInstance(context)
        return restaurantRepo!!.getRestaurants()
    }
}

class MyViewModelFactory(val context: Context) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(context) as T
    }
}