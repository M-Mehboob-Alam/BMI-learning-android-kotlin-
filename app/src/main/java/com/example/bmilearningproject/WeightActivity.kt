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
        val pref = getSharedPreferences(Constant.sharedPrefKey, MODE_PRIVATE)
        val editor = pref.edit()
        var currentValue = pref.getFloat(Constant.weightValueKey, 165f)
        var currentUnit = pref.getString(Constant.weightUnitKey, Constant.unitLbsKey)
        editor.putFloat(Constant.weightValueKey, currentValue)
        editor.putString(Constant.weightUnitKey, currentUnit)

        binding.apply {
            scaleView.setStartingPoint(currentValue)
            scaleView.setUpdateListener { result ->
                changeHeightValue(result.toFloat())
                currentValue = result.toFloat()
                Log.i(TAG, "onCreate: $result")
                editor.putFloat("weightValue", result)
                editor.apply()
            }
            weightValueView.setText(String.format("%.2f", currentValue))
            if (currentUnit === "kg"){
                changeSelectedBg("kg")
                val getWeightValueInKg = getWeightInKg(currentValue)
                weightValueView.setText(String.format("%.2f", getWeightValueInKg))
            }else{
                changeSelectedBg("lbs")
            }
            kgTextView.setOnClickListener {
                if (currentUnit == "lbs") {
                    changeHeightUnit("kg")
                    changeSelectedBg("kg")
                    editor.putString("weightUnit", "kg")
                    val getWeightValue = getWeightInKg(currentValue)
                    currentUnit = "kg"
                    currentValue = getWeightValue
                    editor.putFloat(Constant.weightValueKey, currentValue)
                    editor.putString(Constant.weightUnitKey, currentUnit)
                    editor.apply()
                    weightValueView.setText(String.format("%.2f", currentValue))
                }else{

                }
            }
            libsTxtView.setOnClickListener {

                if (currentUnit == "kg"){
                    changeSelectedBg("lbs")
                    changeHeightUnit("lbs")
                    editor.putString("weightUnit", "lbs")
                    val getWeightValue = getWeightInLbs(currentValue)
                    currentValue = getWeightValue
                    currentUnit = "lbs"
                    editor.putFloat(Constant.weightValueKey, currentValue)
                    weightValueView.setText(String.format("%.2f", currentValue))
                    editor.putString(Constant.weightUnitKey, currentUnit)
                    editor.apply()
                }else{

                }
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
            backIc.setOnClickListener {
                finish()
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