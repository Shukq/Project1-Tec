package com.example.project1.activities

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
import com.example.project1.fragments.FragmentAdapter
import com.example.project1.fragments.ListFragment
import com.example.project1.fragments.MapFragment
import com.example.project1.R
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.app_bar_home2.*




class HomeActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewPager:ViewPager
    private var TAG:String = "MainActivity"
    private lateinit var mFragmentAdapter: FragmentAdapter
    private var email = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        setSupportActionBar(toolbar)

        mFragmentAdapter = FragmentAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.viewpager)
        setupViewPager(viewPager)
        var tabLayout:TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)


        email = this.intent.getStringExtra("email")
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
    }

    fun setupViewPager(viewPager:ViewPager)
    {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(ListFragment(),"Restaurant List")
        adapter.addFragment(MapFragment(),"Mapa")
        viewPager.adapter = adapter

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
        menuInflater.inflate(R.menu.home_activity2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if(item.itemId==R.id.nav_exit)
        {
            Log.i("werwer","ewrew")
        }
        when(item.itemId) {

            R.id.nav_exit -> {
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

            R.id.nav_add -> {
                Log.e("TORUPLOX","STAHP")
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
