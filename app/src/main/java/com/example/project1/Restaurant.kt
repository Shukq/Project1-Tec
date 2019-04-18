package com.example.project1

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Restaurant(
    @PrimaryKey val id:String,
    var name:String,
    var number:Int,
    var cost:String,
    var coordX:Int,
    var coordY:Int,
    var adress:String,
    var rating:MutableList<Int>,
    var images:MutableList<String>
    )





