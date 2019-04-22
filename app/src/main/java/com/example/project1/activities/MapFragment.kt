package com.example.project1.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project1.R
import com.example.project1.dao.DbWorkerThread
import com.example.project1.dao.MyViewModelFactory
import com.example.project1.dao.RestaurantDatabase
import com.example.project1.dao.RestaurantViewModel
import com.example.project1.model.Restaurant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView
import android.widget.Toast
import com.google.android.gms.maps.model.Marker






class MapFragment : Fragment() {
    lateinit var mMapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var viewModel:RestaurantViewModel

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_map_fragment, container, false)
        mMapView = rootView.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)

        mMapView.onResume() // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            googleMap!!.isMyLocationEnabled = true


            val myLocation = LatLng(9.8562991,-83.9125228)
            val cameraPosition = CameraPosition.Builder().target(myLocation).zoom(19f).build()
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


            viewModel = ViewModelProviders.of(this, MyViewModelFactory(activity!!)).get(RestaurantViewModel::class.java)
            viewModel.getRestaurant().observe(this, Observer {
                showMarkers(it?: emptyList())
            })


        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    fun onInfoWindowClick() {

    }

    fun showMarkers(mapList:List<Restaurant>){
        for (rest in mapList) {
            //var snippet = rest.food + "\n" +rest.schedule + "\n" +rest.contactInfo
            var myLocation = LatLng(rest.x.toDouble(),rest.y.toDouble())
            googleMap!!.addMarker(MarkerOptions().position(myLocation).title(rest.name))
            googleMap?.setOnInfoWindowClickListener {
                val intent = Intent(activity, RestaurantDetailsActivity::class.java)
                intent.putExtra("idRest", rest.id)
                startActivity(intent)
            }
        }

    }


}
