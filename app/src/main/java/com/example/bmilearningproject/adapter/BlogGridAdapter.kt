package com.example.bmilearningproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.bmilearningproject.R
import com.example.bmilearningproject.callback.BlogGridMenuListener
import com.example.bmilearningproject.model.BlogGridModel

class BlogGridAdapter(val list: ArrayList<BlogGridModel>, var listener : BlogGridMenuListener): RecyclerView.Adapter<BlogGridAdapter.BlogGridHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlogGridHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_grid_item, parent,false)
        return BlogGridHolder(view)
    }

    override fun onBindViewHolder(
        holder: BlogGridHolder,
        position: Int
    ) {
        holder.mainHeading.text = list[position].mainHeading
        holder.time.text = list[position].time
        holder.blogGridItemLayouot.setOnClickListener {
            listener.onBlogGridMenuClick(list[position])
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    class BlogGridHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        var mainHeading = itemView.findViewById<TextView>(R.id.mainHeading)
        var blogGridItemLayouot = itemView.findViewById<ConstraintLayout>(R.id.blogGridItemLayouot)
        var image = itemView.findViewById<ImageView>(R.id.blogImage)
        var time = itemView.findViewById<TextView>(R.id.time)
    }
}