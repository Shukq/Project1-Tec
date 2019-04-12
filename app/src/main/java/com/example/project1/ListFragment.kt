package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import java.util.ArrayList

class ListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.list)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView!!.setHasFixedSize(true)
        // use a linear layout manager
        layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        val mDividerItemDecoration = DividerItemDecoration(
            recyclerView!!.context,
            LinearLayoutManager.VERTICAL
        )
        recyclerView!!.addItemDecoration(mDividerItemDecoration)
        val myDataset = ArrayList<Restaurant>()
        myDataset.add(Restaurant("1","Mac",12345678,"caro",1,2,"hoy no", mutableListOf<Int>(), mutableListOf<String>()))
        // specify an adapter (see also next example)
        mAdapter = ListAdapter(myDataset){
            val intent = Intent(activity,RestaurantDetailsActivity::class.java)
            startActivity(intent)
        }
        recyclerView!!.adapter = mAdapter




        return view
    }

    companion object {
        private val TAG = "Lista"
    }
}
