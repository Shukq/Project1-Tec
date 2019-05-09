package com.example.project1.dao
import android.arch.lifecycle.LiveData
import android.content.Context
import com.example.project1.model.Restaurant
import javax.inject.Singleton


@Singleton
class RestaurantRepository  {


    private val TAG : String = RestaurantRepository::class.java.simpleName
    private var restaurantDAO: RestaurantDAO? = null

    fun getInstance(context: Context) {
        //beneficiaryDao = Room.databaseBuilder(context.applicationContext,BeneficiaryDatabase::class.java, "beneficiary-database").build().beneficiaryDao()
        restaurantDAO = RestaurantDatabase.getInstance(context)?.RestaurantDAO()

    }



    fun getRestaurants() : LiveData<List<Restaurant>> {
        //refreshBeneficiary()
        //checkIfOffline()
        return restaurantDAO!!.list()
    }

    fun getRestaurant() : LiveData<Restaurant>{

        //refreshRestaurant()
        return restaurantDAO!!.load()
    }

    fun getRest(id : String): Restaurant{
        return restaurantDAO?.getRest(id)!!
    }



    private fun refreshRestaurant(){

    }

}