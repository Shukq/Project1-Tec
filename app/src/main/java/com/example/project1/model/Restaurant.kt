package com.example.project1.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.media.Image

@Entity
data class Restaurant (
    var contactInfo:String,
    var food:String,
    var cost:Int,
    var x:String,
    var y:String,
    var schedule:String,
    var score: ArrayList<String>,
    var images:ArrayList<String>,
    var comments:ArrayList<String>,
    @PrimaryKey
    val id:String,
    var name:String
    )