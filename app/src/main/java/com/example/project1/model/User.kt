package com.example.project1.model

import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("email")
    @Expose
    var email:String,
    @SerializedName("password")
    @Expose
    var password:String



)