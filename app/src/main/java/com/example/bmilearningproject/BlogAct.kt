package com.example.bmilearningproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
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
import com.example.bmilearningproject.callback.BlogGridMenuListener
import com.example.bmilearningproject.callback.BlogTopMenuListener
import com.example.bmilearningproject.databinding.ActivityBlogBinding
import com.example.bmilearningproject.model.BlogGridModel
import com.example.bmilearningproject.model.BlogTopModel
import org.json.JSONArray
import java.io.BufferedReader

class BlogAct : AppCompatActivity(), BlogTopMenuListener, BlogGridMenuListener {

    val TAG = "BlogAct"
    private  lateinit var blogGridAdapter : BlogGridAdapter

    private lateinit var bmiJsonReader :  JSONArray
    private lateinit var bmrJsonReader :  JSONArray
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
            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI"))
            menuList.add(BlogTopModel(R.drawable.admin_panel_settings_ic,  "BMR"))
//            menuList.add(BlogTopModel(R.drawable.bmi_without_bg_ic,  "BMI Blog Act"))
//            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI Blog Act"))
//            menuList.add(BlogTopModel(R.drawable.bmi_logo,  "BMI Blog Act"))

            topBlogRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.HORIZONTAL, false)
//            topBlogRV.layoutManager = GridLayoutManager(this@BlogAct, 3)
            blogTopAdapter = BlogTopAdapter(menuList,this@BlogAct)
            topBlogRV.adapter = BlogTopAdapter(menuList, this@BlogAct)


//            blogTopAdapter  = BlogTopAdapter(menuList)
//            topBlogRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.HORIZONTAL, false)
//            topBlogRV.adapter = blogTopAdapter


//            blogGridList = ArrayList<BlogGridModel>()
//
//            blogGridList.add(BlogGridModel("This is main heading from blogAct"))
//            blogGridList.add(BlogGridModel("This is main heading 2"))
//            blogGridList.add(BlogGridModel("This is main heading 3"))
//            blogGridRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.VERTICAL, false)
//            blogGridAdapter = BlogGridAdapter(blogGridList)
//            blogGridRV.adapter = BlogGridAdapter(blogGridList)
              setBlogGridAdapter("All")

        }
    }

    override fun onBlogTopMenuClick(
        model: BlogTopModel,
        text: View
    ) {
       setBlogGridAdapter(model.tile)

    }

    private fun setBlogGridAdapter( title : String){
        binding.apply {
            blogGridList = ArrayList<BlogGridModel>()
            val bmiJsonFileReader = getJsonFileReader(R.raw.bmi_blog)
            val bmrJsonFileReader = getJsonFileReader(R.raw.bmr_blog)
            bmiJsonReader = JSONArray(bmiJsonFileReader)
            bmrJsonReader = JSONArray(bmrJsonFileReader)
            blogGridRV.layoutManager = LinearLayoutManager(this@BlogAct, LinearLayoutManager.VERTICAL, false)
            if (title === "BMI"){
                for(i in 0 until bmiJsonReader.length()){
                    val bmiJsonObject = bmiJsonReader.getJSONObject(i)
                    val mainHeading = bmiJsonObject.getString("title")
                    val des = bmiJsonObject.getString("description")
                    val parseMainHeading = Html.fromHtml(mainHeading, Html.FROM_HTML_MODE_LEGACY)
                    val parseDes = Html.fromHtml(des, Html.FROM_HTML_MODE_LEGACY)
                    val time = bmiJsonObject.getString("read_time")

                    blogGridList.add(BlogGridModel(parseMainHeading.toString(),time,parseDes.toString()))
                }
//                blogGridList.add(BlogGridModel("This is main heading from BMI","10 min"))
//                blogGridList.add(BlogGridModel("This is main heading 2 BMI"))
//                blogGridList.add(BlogGridModel("This is main heading 3 BMI"))
            }else if(title === "BMR"){
                for(i in 0 until bmrJsonReader.length()){
                    val bmiJsonObject = bmrJsonReader.getJSONObject(i)
                    val mainHeading = bmiJsonObject.getString("title")
                    val des = bmiJsonObject.getString("description")
                    val parseMainHeading = Html.fromHtml(mainHeading, Html.FROM_HTML_MODE_LEGACY)
                    val parseDes = Html.fromHtml(des, Html.FROM_HTML_MODE_LEGACY)
                    val time = bmiJsonObject.getString("read_time")

                    blogGridList.add(BlogGridModel(parseMainHeading.toString(),time,parseDes.toString()))
                }

//                blogGridList.add(BlogGridModel("This is main heading from BMR"))
//                blogGridList.add(BlogGridModel("This is main heading 2 BMR"))
//                blogGridList.add(BlogGridModel("This is main heading 3 BMR"))
            }else{

                for(i in 0 until bmiJsonReader.length()){
                    val bmiJsonObject = bmiJsonReader.getJSONObject(i)
                    val mainHeading = bmiJsonObject.getString("title")
                    val des = bmiJsonObject.getString("description")
                    val parseMainHeading = Html.fromHtml(mainHeading, Html.FROM_HTML_MODE_LEGACY)
                    val parseDes = Html.fromHtml(des, Html.FROM_HTML_MODE_LEGACY)
                    val time = bmiJsonObject.getString("read_time")

                    blogGridList.add(BlogGridModel(parseMainHeading.toString(),time,parseDes.toString()))
                }
//                blogGridList.add(BlogGridModel("This is main heading from BMI"))
//                blogGridList.add(BlogGridModel("This is main heading 2 BMI"))
//                blogGridList.add(BlogGridModel("This is main heading 3 BMI"))
//                blogGridList.add(BlogGridModel("This is main heading from BMR"))
//                blogGridList.add(BlogGridModel("This is main heading 2 BMR"))
//                blogGridList.add(BlogGridModel("This is main heading 3 BMR"))
            }

            blogGridAdapter = BlogGridAdapter(blogGridList,this@BlogAct)
            blogGridRV.adapter = BlogGridAdapter(blogGridList,this@BlogAct)
        }
    }

    override fun onBlogGridMenuClick(model: BlogGridModel) {
       var intent = Intent(this@BlogAct, BlogPostDetailAct::class.java)
//        intent.putExtra("mainHeading", model.mainHeading)
//        intent.putExtra("time", model.time)
        intent.putExtra("model", model)
        startActivity(intent)

    }
    private fun  getJsonFileReader(file:Int) : String {
        val getRawFile = resources.openRawResource(file)
        val getRedJson = BufferedReader(getRawFile.reader())
        return getRedJson.use {
            it.readText()
        }
    }
}