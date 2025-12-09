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
import com.example.bmilearningproject.util.Constant
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
        val currentValue = pref.getFloat(Constant.heightValueKey, 5.7f)
        val currentUnit = pref.getString(Constant.heightUnitKey, "ft")
        editor.putFloat(Constant.heightValueKey, currentValue)
        editor.putString(Constant.heightUnitKey, currentUnit)
        editor.apply()

        binding.apply {
            scaleView.setStartingPoint(currentValue)
            scaleView.setUpdateListener { result ->
                changeHeightValue(result.toFloat())
                Log.i(TAG, "onCreate: $result")
                editor.putFloat(Constant.heightValueKey, result)
                editor.apply()
            }
            cmTxtView.setOnClickListener {
                changeHeightUnit("cm")
                changeSelectedBg("cm")
                editor.putString(Constant.heightUnitKey, "cm")
                editor.apply()
//                Toast.makeText(this@HeightAct, "cm clicked", Toast.LENGTH_SHORT).show()
            }
            ftTxtView.setOnClickListener {
                changeSelectedBg("ft")
                changeHeightUnit("ft")
                editor.putString(Constant.heightUnitKey, "ft")
                editor.apply()
//                Toast.makeText(this@MainActivity, "ft clicked", Toast.LENGTH_SHORT).show()

            }

            nextBtn.setOnClickListener {
                startActivity(Intent(this@HeightAct, DashboardAct::class.java))
                finishAffinity()
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