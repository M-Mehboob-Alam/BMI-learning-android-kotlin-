package com.example.bmilearningproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmilearningproject.R
import com.example.bmilearningproject.model.BlogGridModel

class BlogGridAdapter(val list: ArrayList<BlogGridModel>): RecyclerView.Adapter<BlogGridAdapter.BlogGridHolder>(){
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
    }

    override fun getItemCount(): Int {
       return list.size
    }

    class BlogGridHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        var mainHeading = itemView.findViewById<TextView>(R.id.mainHeading)
    }
}