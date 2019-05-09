package com.example.project1.activities

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.project1.R
import com.example.project1.Utils.SharedPreferencesUtils
import com.example.project1.adapters.CommentAdapter
import com.example.project1.adapters.ImageAdapter
import com.example.project1.adapters.ListAdapter
import com.example.project1.api.RetrofitClient
import com.example.project1.dao.DbWorkerThread
import com.example.project1.dao.RestaurantDatabase
import com.example.project1.dao.RestaurantViewModel
import com.example.project1.model.DefaultResponse
import com.example.project1.model.Restaurant
import kotlinx.android.synthetic.main.activity_restaurant_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetailsActivity : AppCompatActivity() {
    private var idRest = ""
    private lateinit var rest:Restaurant
    private lateinit var dbWorkerThread:DbWorkerThread
    private var myDb:RestaurantDatabase?= null

    private var recyclerView: RecyclerView? = null
    private var mAdapter: ImageAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager?= null
    private lateinit var imgList:ArrayList<String>
    private lateinit var btnComment: ImageButton
    private lateinit var btnScore1: ImageView
    private lateinit var btnScore2: ImageView
    private lateinit var btnScore3: ImageView
    private lateinit var btnScore4: ImageView
    private lateinit var btnScore5: ImageView

    private var recyclerComment: RecyclerView? = null
    private var mAdapterComment: CommentAdapter? = null
    private var layoutManagerComment: RecyclerView.LayoutManager?= null
    private lateinit var commentList:ArrayList<String>
    private var userEmmitScore = false
    private var globalUser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        dbWorkerThread = DbWorkerThread("dbDetailsWorkThread")
        dbWorkerThread.start()




        imgList = arrayListOf()
        recyclerView = findViewById(R.id.recicler_restImages)
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView!!.layoutManager = layoutManager
        mAdapter = ImageAdapter(imgList)
        recyclerView!!.adapter = mAdapter

        commentList = arrayListOf()
        recyclerComment = findViewById(R.id.recycler_restComments)
        recyclerComment!!.setHasFixedSize(true)
        layoutManagerComment = LinearLayoutManager(this)
        recyclerComment!!.layoutManager = layoutManagerComment
        val mDividerItemDecoration = DividerItemDecoration(
            recyclerComment!!.context,
            LinearLayoutManager.VERTICAL
        )
        recyclerComment!!.addItemDecoration(mDividerItemDecoration)
        mAdapterComment = CommentAdapter(commentList)
        recyclerComment!!.adapter = mAdapterComment


        idRest = this.intent.getStringExtra("idRest")

        //GetUser info from sharedPreferences
        globalUser = SharedPreferencesUtils.getStringFromSp(this,"user") ?: ""
        userEmmitScore = SharedPreferencesUtils.getBooleanInSp(this,globalUser+idRest)
        //////

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
            commentList = rest.comments
            mAdapter?.setImages(imgList)
            mAdapterComment?.setComments(commentList)
        }
        dbWorkerThread.postTask(task)

        btnComment = findViewById(R.id.btn_addComment)
        btnComment.setOnClickListener {
            addComment()
        }

        btnScore1 = findViewById(R.id.img_rating1)
        btnScore2 = findViewById(R.id.img_rating2)
        btnScore3 = findViewById(R.id.img_rating3)
        btnScore4 = findViewById(R.id.img_rating4)
        btnScore5 = findViewById(R.id.img_rating5)

        btnScore1.setOnClickListener {
            if(userEmmitScore){
                Toast.makeText(this,"User cant emmit score to this Restaurant",Toast.LENGTH_SHORT).show()
            }
            else{
                addScore(1)
            }
        }
        btnScore2.setOnClickListener {
            if(userEmmitScore){
                Toast.makeText(this,"User cant emmit score to this Restaurant",Toast.LENGTH_SHORT).show()
            }
            else{
                addScore(2)
            }
        }
        btnScore3.setOnClickListener {
            if(userEmmitScore){
                Toast.makeText(this,"User cant emmit score to this Restaurant",Toast.LENGTH_SHORT).show()
            }
            else{
                addScore(3)
            }
        }
        btnScore4.setOnClickListener {
            if(userEmmitScore){
                Toast.makeText(this,"User cant emmit score to this Restaurant",Toast.LENGTH_SHORT).show()
            }
            else{
                addScore(4)
            }
        }
        btnScore5.setOnClickListener {
            if(userEmmitScore){
                Toast.makeText(this,"User cant emmit score to this Restaurant",Toast.LENGTH_SHORT).show()
            }
            else{
                addScore(5)
            }
        }



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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.details_activity, menu)

        return true
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

    fun addComment(){
        val id = idRest
        val comment = editTxt_Comment.text.toString()
        val hash:HashMap<String,Any> = hashMapOf()
        hash.set("id",id)
        hash.set("comment",comment)

        RetrofitClient.instance.addComment(hash)
            .enqueue(object: Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@RestaurantDetailsActivity,"Server error!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@RestaurantDetailsActivity,"Comment posted!", Toast.LENGTH_SHORT).show()
                        editTxt_Comment?.text = Editable.Factory.getInstance().newEditable("")
                        editTxt_Comment.setCursorVisible(false)
                    }
                    else
                    {
                        Toast.makeText(this@RestaurantDetailsActivity,"Cannot post comment!", Toast.LENGTH_SHORT).show()
                        //editTxt_Comment?.text = Editable.Factory.getInstance().newEditable("")
                    }

                }


            })

    }

    fun addScore(rating:Int){
        val id = idRest
        val hash:HashMap<String,Any> = hashMapOf()
        hash.set("id",id)
        hash.set("score",rating.toString())
        RetrofitClient.instance.addScore(hash)
            .enqueue(object: Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@RestaurantDetailsActivity,"Server error!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful)
                    {
                        SharedPreferencesUtils.saveBooleanInSp(this@RestaurantDetailsActivity,globalUser+idRest,true)
                        if(rating == 1)
                        {
                            btnScore1.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore1.isClickable=false
                            btnScore2.isClickable=false
                            btnScore3.isClickable=false
                            btnScore4.isClickable=false
                            btnScore5.isClickable=false
                        }
                        if(rating == 2)
                        {
                            btnScore1.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore2.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore1.isClickable=false
                            btnScore2.isClickable=false
                            btnScore3.isClickable=false
                            btnScore4.isClickable=false
                            btnScore5.isClickable=false
                        }
                        if(rating == 3)
                        {
                            btnScore1.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore2.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore3.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore1.isClickable=false
                            btnScore2.isClickable=false
                            btnScore3.isClickable=false
                            btnScore4.isClickable=false
                            btnScore5.isClickable=false
                        }
                        if(rating == 4)
                        {
                            btnScore1.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore2.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore3.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore4.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore1.isClickable=false
                            btnScore2.isClickable=false
                            btnScore3.isClickable=false
                            btnScore4.isClickable=false
                            btnScore5.isClickable=false
                        }
                        if(rating == 5)
                        {
                            btnScore1.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore2.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore3.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore4.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore5.setBackgroundResource(R.drawable.ic_star_black_24dp)
                            btnScore1.isClickable=false
                            btnScore2.isClickable=false
                            btnScore3.isClickable=false
                            btnScore4.isClickable=false
                            btnScore5.isClickable=false
                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        Toast.makeText(this@RestaurantDetailsActivity,"Error!", Toast.LENGTH_SHORT).show()

                    }

                }


            })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }


}
