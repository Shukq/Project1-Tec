package com.example.project1.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.project1.R
import com.squareup.picasso.Picasso

class ImageAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var mImages=images

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var img : ImageView = itemView.findViewById(R.id.img_Rest)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.recycle_imagerest, p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mImages.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val image = mImages[p1]
        if(mImages.isNotEmpty())
        {
            Picasso.get().load(image).error(R.drawable.logo_res).into(p0.img)
        }
    }



    fun setImages(imgList:List<String>){
        mImages = imgList
        notifyDataSetChanged()
    }
}


