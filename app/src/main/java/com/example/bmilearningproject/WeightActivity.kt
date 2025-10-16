package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityWeightBinding
import java.util.Locale

class WeightActivity : AppCompatActivity() {
    private  val TAG = "WeightActivity"
    private lateinit var binding: ActivityWeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = getSharedPreferences("BmiCalculator", MODE_PRIVATE)
        val editor = pref.edit()

        binding.apply {
            backIc.setOnClickListener {
                finish()
            }
            kgTextView.setOnClickListener {
                changeHeightUnit("kg")
                changeSelectedBg("kg")
                editor.putString("weightUnit", "kg")

//                Toast.makeText(this@WeightActivity, "kg clicked", Toast.LENGTH_SHORT).show()
            }
            libsTxtView.setOnClickListener {
                changeSelectedBg("libs")
                changeHeightUnit("libs")
                editor.putString("weightUnit", "libs")
                editor.apply()
//                Toast.makeText(this@MainActivity, "libs clicked", Toast.LENGTH_SHORT).show()

            }
            scaleView.setStartingPoint(65f)
            scaleView.setUpdateListener { result ->
                changeHeightValue(result.toFloat())
                Log.i(TAG, "onCreate: $result")
                editor.putFloat("weightValue", result)
                editor.apply()
            }


            nextBtn.setOnClickListener {
                startActivity(Intent(this@WeightActivity, HeightAct::class.java))
//                finish()
            }
        }
    }
    private fun changeHeightUnit(unit: String){
        binding.weightUnit.text = unit

    }

    private fun changeHeightValue(value : Float){
//        float value 2 decimal places
        binding.heightValue.text =String.format(Locale.getDefault(), "%.2f", value)

    }
    private fun changeSelectedBg(getUnit: String){

        if (getUnit === "kg"){
            binding.kgTextView.setBackgroundResource(R.drawable.bg_common)
            binding.kgTextView.setTextColor(resources.getColor(R.color.white))
            binding.libsTxtView.setBackgroundResource(R.drawable.bg_white)
            binding.libsTxtView.setTextColor(resources.getColor(R.color.black))
        }else{
            binding.kgTextView.setBackgroundResource(R.drawable.bg_white)
            binding.kgTextView.setTextColor(resources.getColor(R.color.black))
            binding.libsTxtView.setBackgroundResource(R.drawable.bg_common)
            binding.libsTxtView.setTextColor(resources.getColor(R.color.white))
        }
    }
}