package com.example.project1.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project1.R
import com.example.project1.adapters.ListAdapter
import com.example.project1.dao.MyViewModelFactory
import com.example.project1.dao.RestaurantViewModel
import com.example.project1.model.Restaurant

import java.util.ArrayList

class ListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager?= null
    private lateinit var viewModel:RestaurantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_list_fragment, container, false)
        var myDataset = ArrayList<Restaurant>()
        viewModel = ViewModelProviders.of(this,MyViewModelFactory(activity!!)).get(RestaurantViewModel::class.java)
        recyclerView = view.findViewById(R.id.list)
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        val mDividerItemDecoration = DividerItemDecoration(
            recyclerView!!.context,
            LinearLayoutManager.VERTICAL
        )
        recyclerView!!.addItemDecoration(mDividerItemDecoration)
        mAdapter = ListAdapter(myDataset) {
            val intent = Intent(activity, RestaurantDetailsActivity::class.java)
            intent.putExtra("idRest", it.id)
            startActivity(intent)
        }
        recyclerView!!.adapter = mAdapter
        viewModel.getRestaurant().observe(this, Observer {
            mAdapter!!.setRestaurants(it?: emptyList())
        })


        /*var myDataset = ArrayList<Restaurant>()
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

            })*/





        // specify an adapter (see also next example)





        return view
    }

    companion object {
        private val TAG = "Lista"
    }
}
