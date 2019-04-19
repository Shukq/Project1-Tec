package com.example.project1.fragments

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
import com.example.project1.activities.RestaurantDetailsActivity
import com.example.project1.R
import com.example.project1.api.RetrofitClient
import com.example.project1.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        var myDataset = ArrayList<Restaurant>()
        RetrofitClient.instance.getRestaurant()
            .enqueue(object:Callback<ArrayList<Restaurant>>{
                override fun onFailure(call: Call<ArrayList<Restaurant>>, t: Throwable) {
                    Log.e("ERROR",t.toString())
                }

                override fun onResponse(call:Call<ArrayList<Restaurant>>, response: Response<ArrayList<Restaurant>>) {

                    if(response.body()!=null)
                    {
                        myDataset = response.body()!!
                        mAdapter = ListAdapter(myDataset) {
                            val intent = Intent(activity, RestaurantDetailsActivity::class.java)
                            startActivity(intent)
                        }
                        recyclerView!!.adapter = mAdapter
                    }
                }

            })




        /*myDataset.add(
            Restaurant(
                "1",
                "Mac",
                12345678,
                "caro",
                1,
                2,
                "hoy no"
                //mutableListOf<Int>(),
                //mutableListOf<String>()
            )
        )*/
        // specify an adapter (see also next example)





        return view
    }

    companion object {
        private val TAG = "Lista"
    }
}
