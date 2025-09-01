package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivitySplashBinding

class SplashAct : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val TAG = "SplashAct"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = getSharedPreferences("BmiCalculator", MODE_PRIVATE)
        val unit = pref.getString("unit", "cm")
        val heightValue = pref.getFloat("heightValue", 5.0f)
        Log.i(TAG, "onCreate: $unit $heightValue ")
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.title.text = "WELCOME TO BMI"

            }, 3000
        )
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this@SplashAct, HeightAct::class.java)
                startActivity(intent)
                finish()
            }, 7000
        )
//        binding.apply {
//            val intent = Intent(this@SplashAct, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}