package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityAgeBinding
import com.example.bmilearningproject.util.Constant
import java.util.Locale

class AgeAct : AppCompatActivity() {
    private lateinit var binding : ActivityAgeBinding
    private  val TAG = "AgeAct"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = getSharedPreferences("BmiCalculator", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putFloat(Constant.ageValueKey, 25F)

        binding.apply {
            backIc.setOnClickListener {
                finish()
            }
            scaleView.setStartingPoint(25f)
            scaleView.setUpdateListener { result ->
                changeAgeValue(result.toInt())
                Log.i(TAG, "onCreate: $result")
                editor.putString(Constant.ageUnitKey, "y")
                editor.putFloat(Constant.ageValueKey, result)
                editor.apply()
            }

            editor.apply()
            nextBtn.setOnClickListener {
                startActivity(Intent(this@AgeAct, WeightActivity::class.java))
//                finish()
            }
        }
    }
    private fun changeHeightUnit(unit: String){
        binding.weightUnit.text = unit

    }
    private fun changeAgeValue(value : Int){
//        float value 2 decimal places
//        Toast.makeText(this@AgeAct, "get age value from scale " + value, Toast.LENGTH_SHORT).show()
        binding.ageValue.text =value.toString()

    }

}