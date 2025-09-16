package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityMainBinding
import java.util.Locale

class HeightAct : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    //      initialize shared preference

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
        val pref = getSharedPreferences("BmiCalculator", MODE_PRIVATE)
        //        create editor
        val editor = pref.edit()

        binding.apply {
            cmTxtView.setOnClickListener {
                changeHeightUnit("cm")
                changeSelectedBg("cm")
                editor.putString("heightUnit", "cm")
                Toast.makeText(this@HeightAct, "cm clicked", Toast.LENGTH_SHORT).show()
            }
            ftTxtView.setOnClickListener {
                changeSelectedBg("ft")
                changeHeightUnit("ft")
                editor.putString("heightUnit", "ft")
//                Toast.makeText(this@MainActivity, "ft clicked", Toast.LENGTH_SHORT).show()

            }
            scaleView.setStartingPoint(165f)
            scaleView.setUpdateListener { result ->
                changeHeightValue(result.toFloat())
                Log.i(TAG, "onCreate: $result")
                editor.putFloat("heightValue", result)
            }

            editor.apply()
            nextBtn.setOnClickListener {
                startActivity(Intent(this@HeightAct, DashboardAct::class.java))
                finish()
            }
//
        }

    }

    private fun changeHeightUnit(unit: String){
        binding.heightUnit.text = unit

    }

    private fun changeHeightValue(value : Float){
//        float value 2 decimal places
        binding.heightValue.text =String.format(Locale.getDefault(), "%.2f", value)

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