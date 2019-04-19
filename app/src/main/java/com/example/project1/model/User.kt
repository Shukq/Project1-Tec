package com.example.project1.model

import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Entity

@Entity
class User(
    @PrimaryKey val id:String,
    var email:String,
    var password:String



)