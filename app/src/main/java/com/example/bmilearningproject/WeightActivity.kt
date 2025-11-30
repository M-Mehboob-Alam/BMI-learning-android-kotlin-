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
import com.example.bmilearningproject.util.Constant
import com.example.bmilearningproject.util.getWeightInKg
import com.example.bmilearningproject.util.getWeightInLbs
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
        var getChangeValueOfScale = pref.getFloat(Constant.weightValueKey, 65f)
        var getCurrentSelectedWeightUnit = pref.getString(Constant.weightUnitKey, "libs")
        binding.apply {
            scaleView.setStartingPoint(getChangeValueOfScale)
            scaleView.setUpdateListener { result ->
                changeHeightValue(result.toFloat())
                getChangeValueOfScale = result.toFloat()
                Log.i(TAG, "onCreate: $result")
                editor.putFloat("weightValue", result)
                editor.apply()
            }
            weightValueView.setText(String.format("%.2f", getChangeValueOfScale))
            if (getCurrentSelectedWeightUnit === "kg"){
                changeSelectedBg("kg")
                val getWeightValueInKg = getWeightInKg(getChangeValueOfScale)
                weightValueView.setText(String.format("%.2f", getWeightValueInKg))
            }else{
                changeSelectedBg("libs")
            }
            backIc.setOnClickListener {
                finish()
            }
            kgTextView.setOnClickListener {
                changeHeightUnit("kg")
                changeSelectedBg("kg")
                editor.putString("weightUnit", "kg")
                val getWeightValue = getWeightInKg(getChangeValueOfScale)
                    editor.putFloat(Constant.weightValueKey, getWeightValue)
                    editor.putString(Constant.weightUnitKey, "kg")
                    editor.apply()
                weightValueView.setText(String.format("%.2f", getWeightValue))

//                Toast.makeText(this@WeightActivity, "kg clicked", Toast.LENGTH_SHORT).show()
            }
            libsTxtView.setOnClickListener {
                changeSelectedBg("libs")
                changeHeightUnit("libs")
                editor.putString("weightUnit", "libs")
                val getWeightValue = getWeightInLbs(getChangeValueOfScale)
                weightValueView.setText(String.format("%.2f", getWeightValue))
                editor.putFloat(Constant.weightValueKey, getWeightValue)
                editor.putString(Constant.weightUnitKey, "libs")
                editor.apply()
//              Toast.makeText(this@MainActivity, "libs clicked", Toast.LENGTH_SHORT).show()

            }

            nextBtn.setOnClickListener {
                val getSettingCompleted = pref.getBoolean(Constant.isCompletedSettingKey, false)
                if (getSettingCompleted){
                    startActivity(Intent(this@WeightActivity, DashboardAct::class.java))
                }else{
                    startActivity(Intent(this@WeightActivity, HeightAct::class.java))
                }
//                finish()
            }
        }
    }
    private fun changeHeightUnit(unit: String){
        binding.weightUnit.text = unit

    }

    private fun changeHeightValue(value : Float){
//        float value 2 decimal places
        binding.weightValueView.text =String.format(Locale.getDefault(), "%.2f", value)

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