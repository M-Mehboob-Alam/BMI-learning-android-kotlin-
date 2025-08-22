package com.example.bmilearningproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityMainBinding
import com.example.bmilearningproject.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            cmTxtView.setOnClickListener {
                changeHeightUnit("cm")
                changeSelectedBg("cm")
//                Toast.makeText(this@MainActivity, "cm clicked", Toast.LENGTH_SHORT).show()
            }
            ftTxtView.setOnClickListener {
                changeSelectedBg("ft")
                changeHeightUnit("ft")
//                Toast.makeText(this@MainActivity, "ft clicked", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun changeHeightUnit(unit: String){
        binding.heightUnit.text = unit
    }
    private fun changeSelectedBg(getUnit: String){
        if (getUnit === "cm"){
            binding.cmTxtView.setBackgroundResource(R.drawable.bg_common)
            binding.cmTxtView.setTextColor(resources.getColor(R.color.white))
            binding.ftTxtView.setBackgroundResource(R.drawable.bg_white)
            binding.ftTxtView.setTextColor(resources.getColor(R.color.black))
        }else{
            binding.cmTxtView.setBackgroundResource(R.drawable.bg_white)
            binding.cmTxtView.setTextColor(resources.getColor(R.color.black))
            binding.ftTxtView.setBackgroundResource(R.drawable.bg_common)
            binding.ftTxtView.setTextColor(resources.getColor(R.color.white))
        }
    }

}