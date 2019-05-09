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
{
    fun setAvg():Int{
        var avg = 0
        if(this.score.isNotEmpty())
        {
            for (i in this.score)
            {
                avg += i.toInt()
            }
            avg = avg.div(this.score.size)
            return avg
        }
        else
        {
            return 0
        }

    }
}

