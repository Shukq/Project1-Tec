package com.example.project1.dao
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.project1.model.Restaurant

@Dao
abstract class RestaurantDAO {
    @Insert(onConflict = REPLACE)
    abstract fun save(restaurant: Restaurant)
    @Insert(onConflict = REPLACE)
    abstract fun saveList(list : List<Restaurant>)
    @Query("SELECT * FROM Restaurant")
    abstract fun load() : LiveData<Restaurant>
    @Query("SELECT * FROM Restaurant")
    abstract fun list(): LiveData<List<Restaurant>>
    @Query("DELETE from Restaurant")
    abstract fun deleteAll()
    @Delete
    abstract fun deleteRestaurant(restaurant: Restaurant)
    @Query("DELETE FROM Restaurant WHERE id = :citizenIdDelete")
    abstract fun deleteById(citizenIdDelete : String)
    @Update
    abstract fun update(restaurant: Restaurant)

}