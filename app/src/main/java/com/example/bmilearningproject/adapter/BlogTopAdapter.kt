package com.example.bmilearningproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.bmilearningproject.R
import com.example.bmilearningproject.callback.BlogTopMenuListener
import com.example.bmilearningproject.model.BlogTopModel

class BlogTopAdapter(val list: ArrayList<BlogTopModel>,var listener : BlogTopMenuListener) : RecyclerView.Adapter<BlogTopAdapter.BlogTopHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlogTopHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent,false)
       return BlogTopHolder(view)
    }

    override fun onBindViewHolder(
        holder: BlogTopHolder,
        position: Int
    ) {
       holder.image.setImageResource(list[position].image)
        holder.name.text = list[position].tile
        holder.blogTopItem.setOnClickListener {
            listener.onBlogTopMenuClick(list[position], holder.name)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class  BlogTopHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.blogItemImg)
        val name = itemView.findViewById<TextView>(R.id.blogItemText)
        val blogTopItem = itemView.findViewById<ConstraintLayout>(R.id.blogItemLayout)
    }
}