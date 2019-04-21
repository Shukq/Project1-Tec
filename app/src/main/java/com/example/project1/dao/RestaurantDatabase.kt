package com.example.project1.dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.project1.model.Converters
import com.example.project1.model.Restaurant

@Database(entities = arrayOf(Restaurant::class), version = 3)
@TypeConverters(Converters::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun RestaurantDAO() : RestaurantDAO

    companion object {
        private var INSTANCE : RestaurantDatabase? = null

        fun getInstance(context: Context) : RestaurantDatabase?{
            if(INSTANCE==null){
                synchronized(RestaurantDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RestaurantDatabase::class.java, "restaurant.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}