package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityBmrBinding
import com.example.bmilearningproject.util.Constant
import com.example.bmilearningproject.util.getCurrentActiveIndicator
import com.example.bmilearningproject.util.getHeightUnit
import com.example.bmilearningproject.util.getWeightUnit

class BmrAct : AppCompatActivity() {
    private lateinit var binding: ActivityBmrBinding
    val TAG = "BmrAct"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = getSharedPreferences(Constant.sharedPrefKey, MODE_PRIVATE)
        val editor = pref.edit()
        val gender = pref.getString(Constant.genderKey, "male")
        val heightUnit = pref.getString("heightUnit", "cm")
        val heightValue = pref.getFloat("heightValue", 0f)
        val weightUnit = pref.getString("weightUnit", "kg")
        val weightValue = pref.getFloat("weightValue", 0f)
        val ageUnit = pref.getString("ageUnit", "y")
        val ageValue = pref.getFloat("ageValue", 0f)
        val isCompletedSetting = pref.getBoolean(Constant.isCompletedSettingKey, false)
        if(!isCompletedSetting){
            editor.putBoolean(Constant.isCompletedSettingKey, true)
            editor.apply()
        }

        Log.i(TAG, "Gender: $gender  Height: $heightUnit $heightValue Weight: $weightUnit $weightValue Age: $ageUnit $ageValue isCompletedSetting: $isCompletedSetting")
        binding.apply {

            if (gender =="female"){
//                Log.i(TAG, "onCreate: gender is female selected")
//                Toast.makeText(this@DashboardAct, "selected gender is female", Toast.LENGTH_SHORT).show()
                genderFemale.setCardBackgroundColor(getColor(R.color.common_color))
                genderMale.setCardBackgroundColor(getColor(R.color.card_color))
                female.setTextColor(getColor(R.color.white))
                male.setTextColor(getColor(R.color.common_color_light))
            }else{
                genderMale.setCardBackgroundColor(getColor(R.color.common_color))
                genderFemale.setCardBackgroundColor(getColor(R.color.card_color))
                male.setTextColor(getColor(R.color.white))
                female.setTextColor(getColor(R.color.common_color_light))

            }
            ageValueView.setText(String.format("%.2f", ageValue))
            weightValueView.setText(String.format("%.2f", weightValue))
            heightValueView.setText(String.format("%.2f", heightValue))
                getWeightUnit(this@BmrAct, weightUnit, libsTextView, kgTextView)
            kgTextView.setOnClickListener {
                getWeightUnit(this@BmrAct, "kg", libsTextView, kgTextView)
            }
            libsTextView.setOnClickListener {
                getWeightUnit(this@BmrAct, "libs", libsTextView, kgTextView)
            }
            getHeightUnit(this@BmrAct, heightUnit, ftTextView, cmTextView)
            ftTextView.setOnClickListener {
                getHeightUnit(this@BmrAct, "ft", ftTextView, cmTextView)
            }
            cmTextView.setOnClickListener {
                getHeightUnit(this@BmrAct, "cm", ftTextView, cmTextView)
            }

            binding.apply {
            backIc.setOnClickListener {
                finish()
            }
                    nextBtn.setOnClickListener {
                        val intent = Intent(this@BmrAct, ResultAct::class.java)
                        intent.putExtra("name", "BMR")
                        startActivity(intent)

                    }
        }
    }
}
}