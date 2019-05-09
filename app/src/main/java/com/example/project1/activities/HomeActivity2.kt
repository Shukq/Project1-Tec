package com.example.project1.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.project1.adapters.FragmentAdapter
import com.example.project1.R
import com.example.project1.api.RetrofitClient
import com.example.project1.dao.DbWorkerThread
import com.example.project1.dao.RestaurantDatabase
import com.example.project1.model.Restaurant
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.app_bar_home2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.example.project1.Utils.SharedPreferencesUtils


class HomeActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewPager:ViewPager
    private var TAG:String = "MainActivity"
    private lateinit var mFragmentAdapter: FragmentAdapter
    private var email = ""
    private lateinit var dbWorkerThread:DbWorkerThread
    private var myDb:RestaurantDatabase?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        setSupportActionBar(toolbar)

        mFragmentAdapter = FragmentAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.viewpager)
        setupViewPager(viewPager)
        var tabLayout:TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        //dbWorkerThread = DbWorkerThread("dbWorkerThread")
        //dbWorkerThread.start()
        myDb = RestaurantDatabase.getInstance(this)


        email = this.intent.getStringExtra("email")
        SharedPreferencesUtils.saveStringInSp(this,"user", email)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.txt_email_nav) as TextView
        navUsername.text = email


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            com.example.project1.R.string.navigation_drawer_open,
            com.example.project1.R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        refreshRepo()
    }
    override fun onDestroy() {
        dbWorkerThread.quit()
        RestaurantDatabase.destroyInstance()
        super.onDestroy()
    }


    fun setupViewPager(viewPager:ViewPager)
    {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(ListFragment(),"Restaurant List")
        adapter.addFragment(MapFragment(),"Mapa")
        viewPager.adapter = adapter

    }

    fun refreshRepo()
    {
        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
        var myDataset = ArrayList<Restaurant>()
        RetrofitClient.instance.getRestaurant()
            .enqueue(object: Callback<ArrayList<Restaurant>> {
                override fun onFailure(call: Call<ArrayList<Restaurant>>, t: Throwable) {
                    Log.e("ERROR",t.toString())
                }

                override fun onResponse(call: Call<ArrayList<Restaurant>>, response: Response<ArrayList<Restaurant>>) {

                    if(response.body()!=null)
                    {
                        myDataset = response.body()!!
                        val task = Runnable {
                            myDb?.RestaurantDAO()?.deleteAll()
                            myDb?.RestaurantDAO()?.saveList(myDataset)
                        }
                        dbWorkerThread.postTask(task)
                    }

                }


            })

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val dialogBack = AlertDialog.Builder(this)
            dialogBack.setTitle("Exit")
            dialogBack.setMessage("Do you want to close session?")
            dialogBack.setPositiveButton("Yes"){
                dialog,_ ->
                dialog.dismiss()
                finish()
            }
            dialogBack.setNegativeButton("No"){
                dialog,_ ->
                dialog.dismiss()
            }
            val d = dialogBack.create()
            d.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.project1.R.menu.home_activity2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_addRefresh -> {
                refreshRepo()
                return true
            }
            R.id.action_addRest -> {
                val intent = Intent(this, AddRestActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_addSearch -> {
                val intent = Intent(this, SearchRestActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when(item.itemId) {

            com.example.project1.R.id.nav_exit -> {
                Log.e("HALP","HALP2")
                val dialogBack = AlertDialog.Builder(this)
                dialogBack.setTitle("Exit")
                dialogBack.setMessage("Do you want to close session?")
                dialogBack.setPositiveButton("Yes"){
                        dialog,_ ->
                    dialog.dismiss()
                    finish()
                    }
                dialogBack.setNegativeButton("No"){
                        dialog,_ ->
                    dialog.dismiss()
                }
                val d = dialogBack.create()
                d.show()
            }

            com.example.project1.R.id.nav_add -> {
                Log.e("TORUPLOX","STAHP")
            }



        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
