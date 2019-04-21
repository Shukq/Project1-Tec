package com.example.project1.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.project1.R
import com.example.project1.adapters.ImageAdapter
import com.example.project1.adapters.ListAdapter
import com.example.project1.dao.DbWorkerThread
import com.example.project1.dao.RestaurantDatabase
import com.example.project1.dao.RestaurantViewModel
import com.example.project1.model.Restaurant
import kotlinx.android.synthetic.main.activity_restaurant_details.*

class RestaurantDetailsActivity : AppCompatActivity() {
    private var idRest = ""
    private lateinit var rest:Restaurant
    private lateinit var dbWorkerThread:DbWorkerThread
    private var myDb:RestaurantDatabase?= null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: ImageAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager?= null
    private lateinit var imgList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)


        imgList = arrayListOf()
        recyclerView = findViewById(R.id.recicler_restImages)
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView!!.layoutManager = layoutManager
        mAdapter = ImageAdapter(imgList)
        recyclerView!!.adapter = mAdapter


        idRest = this.intent.getStringExtra("idRest")
        dbWorkerThread = DbWorkerThread("dbDetailsWorkThread")
        dbWorkerThread.start()
        myDb = RestaurantDatabase.getInstance(this)
        val task = Runnable {
            rest = myDb?.RestaurantDAO()?.getRest(idRest)!!
            txt_restName.text = rest.name
            txt_restType.text = "Type of food: " + rest.food
            txt_restCost.text = "Cost: " + getCost()
            txt_restScore.text = getAvg()
            txt_restSchedule.text = "Open on: " + rest.schedule
            txt_restContact.text = "Contact info: " + rest.contactInfo
            txt_restXY.text = "Location: "+ rest.x + ":" + rest.y
            imgList = rest.images
            mAdapter?.setImages(imgList)
        }
        dbWorkerThread.postTask(task)




        val toolbar : Toolbar = findViewById(R.id.toolbar_details)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


    }

    override fun onDestroy() {
        dbWorkerThread.quit()
        RestaurantDatabase.destroyInstance()
        super.onDestroy()
    }

    fun getCost():String{
        var cost = rest.cost
        if(cost == 0)
        {
            return "$"
        }
        if(cost == 1)
        {
            return "$$"
        }
        return "$$$"
    }

    fun getAvg():String{
        var avg = 0
        if(rest.score.isNotEmpty())
        {
            for (i in rest.score)
            {
                avg += i.toInt()
            }
            avg = avg.div(rest.score.size)
            return avg.toString()
        }
        else
        {
          return "0"
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}
