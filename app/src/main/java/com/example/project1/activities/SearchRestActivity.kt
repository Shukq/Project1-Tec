package com.example.project1.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.*
import com.example.project1.R
import com.example.project1.adapters.SearchAdapter
import com.example.project1.dao.MyViewModelFactory
import com.example.project1.dao.RestaurantViewModel
import com.example.project1.model.Restaurant
import com.google.android.gms.common.util.Strings
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_search_rest.*
import java.util.ArrayList
import java.lang.Integer.parseInt


class SearchRestActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var mAdapter: SearchAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager?= null
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var btnFitler: Button
    private lateinit var searchName:EditText
    private lateinit var spinnerType:Spinner
    private lateinit var spinnerCost:Spinner
    private lateinit var rating:Switch
    private var typeList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(com.example.project1.R.layout.activity_search_rest)

        btnFitler = findViewById(R.id.btn_filter)
        searchName = findViewById(R.id.input_nameSearch)
        spinnerType = findViewById(R.id.spinner_typeSearch)
        spinnerCost =findViewById(R.id.spinner_costSearch)
        rating = findViewById(R.id.switch_rating)


        viewModel = ViewModelProviders.of(this, MyViewModelFactory(this)).get(RestaurantViewModel::class.java)
        viewModel.getRestaurant().observe(this, Observer {
            typeList = loadTypes(it?: emptyList())
            val dynamicAdapter = ArrayAdapter<String>(this, R.layout.spinner_item,typeList)

            // Specify the layout to use when the list of choices appears
            dynamicAdapter
                .setDropDownViewResource(R.layout.spinner_dropdown_item)

            // Apply the adapter to the spinner
            spinnerType.adapter = dynamicAdapter
        })

        val staticAdapter = ArrayAdapter
            .createFromResource(
                this, R.array.spin_costSearch,
                R.layout.spinner_item
            )

        // Specify the layout to use when the list of choices appears
        staticAdapter
            .setDropDownViewResource(R.layout.spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerCost.adapter = staticAdapter

        var myDataset = ArrayList<Restaurant>()
        recyclerView = findViewById(R.id.recycler_searchRest)
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        val mDividerItemDecoration = DividerItemDecoration(
            recyclerView!!.context,
            LinearLayoutManager.VERTICAL
        )
        recyclerView!!.addItemDecoration(mDividerItemDecoration)
        mAdapter = SearchAdapter(myDataset) {
            val intent = Intent(this, RestaurantDetailsActivity::class.java)
            intent.putExtra("idRest", it.id)
            startActivity(intent)
        }
        recyclerView!!.adapter = mAdapter



        btnFitler.setOnClickListener {
            loadSearch()
        }

        val toolbar : Toolbar = findViewById(R.id.toolbar_search)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    fun loadSearch()
    {
        viewModel.getRestaurant().observe(this, Observer {
            var load = filterList(it?: emptyList())
            mAdapter!!.setRestaurants(load)
        })
    }

    fun filterList(restList:List<Restaurant>): List<Restaurant>
    {
        /*
        searchName = findViewById(R.id.input_nameSearch)
        spinnerType = findViewById(R.id.spinner_typeSearch)
        spinnerCost =findViewById(R.id.spinner_costSearch)
        rating = findViewById(R.id.switch_rating)
        */

        var listTemp:MutableList<Restaurant> = mutableListOf()
        if(spinnerType.selectedItem != "No type")
        {
            for (i in restList)
            {
                if(i.name.trim().contains(searchName.text.toString().trim())){
                    if(i.food == spinnerType.selectedItem) {
                        listTemp.add(i)
                    }

                }

            }
        }
        else
        {
            for (i in restList)
            {
                if(i.name.trim().contains(searchName.text.toString().trim())){
                    listTemp.add(i)
                }

            }
        }

        if(spinnerCost.selectedItem == "Ascendant cost")
        {
            listTemp.sortBy { it.cost }
        }
        else
        {
            listTemp.sortByDescending { it.cost }
        }

        if(switch_rating.isChecked)
        {
            listTemp.sortBy { it.setAvg() }
        }
        if(spinnerCost.selectedItem == "Ascendant cost" && switch_rating.isChecked)
        {
            listTemp.sortBy { it.cost }
        }
        if(spinnerCost.selectedItem == "Descent cost" && switch_rating.isChecked)
        {
            listTemp.sortByDescending { it.cost }
        }





       return listTemp
    }

    fun loadTypes(restList:List<Restaurant>):ArrayList<String>
    {
        var resp = ArrayList<String>()
        for (i in restList)
        {
            if(i.food.isNotEmpty()){
                resp.add(i.food)
            }
        }
        resp.add(0,"No type")
        return resp
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }



}
