package com.example.bmilearningproject

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmilearningproject.adapter.BlogGridAdapter
import com.example.bmilearningproject.adapter.BlogTopAdapter
import com.example.bmilearningproject.databinding.ActivityBlogBinding
import com.example.bmilearningproject.model.BlogGridModel
import com.example.bmilearningproject.model.BlogTopModel

class BlogAct : AppCompatActivity() {

    val TAG = "BlogAct"
    private  lateinit var blogGridAdapter : BlogGridAdapter
    private  lateinit var blogGridList : ArrayList<BlogGridModel>
    private lateinit var blogTopAdapter : BlogTopAdapter
    private lateinit var menuList : ArrayList<BlogTopModel>
    private lateinit var binding: ActivityBlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.apply {
            backIc.setOnClickListener {
                finish()
            }

            menuList = ArrayList<BlogTopModel>()
            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI Blog Act"))
            menuList.add(BlogTopModel(R.drawable.admin_panel_settings_ic,  "BMI Blog Act"))
            menuList.add(BlogTopModel(R.drawable.bmi_without_bg_ic,  "BMI Blog Act"))
            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI Blog Act"))
            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI Blog Act"))

            topBlogRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.HORIZONTAL, false)
//            topBlogRV.layoutManager = GridLayoutManager(this@BlogAct, 3)
            blogTopAdapter = BlogTopAdapter(menuList)
            topBlogRV.adapter = BlogTopAdapter(menuList)


//            blogTopAdapter  = BlogTopAdapter(menuList)
//            topBlogRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.HORIZONTAL, false)
//            topBlogRV.adapter = blogTopAdapter


            blogGridList = ArrayList<BlogGridModel>()

            blogGridList.add(BlogGridModel("This is main heading from blogAct"))
            blogGridList.add(BlogGridModel("This is main heading 2"))
            blogGridRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.VERTICAL, false)
            blogGridAdapter = BlogGridAdapter(blogGridList)
            blogGridRV.adapter = BlogGridAdapter(blogGridList)


        }
    }
}