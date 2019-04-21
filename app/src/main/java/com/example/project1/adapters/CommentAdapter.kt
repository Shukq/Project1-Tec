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

class CommentAdapter(private val comments: List<String>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var mComments=comments

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var comment : TextView = itemView.findViewById(R.id.txt_Comment)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.recycle_commentrest, p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mComments.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val comment = mComments[p1]
        p0.comment.text = comment

    }



    fun setComments(comments:List<String>){
        mComments = comments
        notifyDataSetChanged()
    }
}