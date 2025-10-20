package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityDashboardBinding
import com.example.bmilearningproject.util.Constant
import com.example.bmilearningproject.util.getCurrentActiveIndicator
import com.example.bmilearningproject.util.getHeightUnit
import com.example.bmilearningproject.util.getWeightUnit

class DashboardAct : AppCompatActivity() {
    val TAG = "DashboardAct"
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)

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
            settingIc.setOnClickListener {
                startActivity(Intent(this@DashboardAct, SettingAct::class.java))
                finish()
            }
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
            getWeightUnit(this@DashboardAct, weightUnit, libsTextView, kgTextView)
            kgTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "kg", libsTextView, kgTextView)
            }
            libsTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "libs", libsTextView, kgTextView)
            }
            getHeightUnit(this@DashboardAct, heightUnit, ftTextView, cmTextView)
            ftTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "ft", ftTextView, cmTextView)
            }
            cmTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "cm", ftTextView, cmTextView)
            }
            bmr.setOnClickListener {
                getCurrentActiveIndicator(this@DashboardAct, "bmr", bmiCard, bmrCard, blogCard, bmi, bmr, blog)

                startActivity(Intent(this@DashboardAct, BmrAct::class.java))
            }
            bmi.setOnClickListener {
                getCurrentActiveIndicator(this@DashboardAct, "bmi", bmiCard, bmrCard, blogCard, bmi, bmr, blog)
                startActivity(Intent(this@DashboardAct, BlogAct::class.java))
            }



//            if (heightUnit == "ft"){
//                cmTextView.setTextColor(getColor(R.color.common_color_light))
//                cmTextView.setBackgroundResource(R.drawable.bg_white)
//                ftTextView.setTextColor(getColor(R.color.white))
//                ftTextView.setBackgroundResource(R.drawable.bg_common)
//            }else{
//                cmTextView.setTextColor(getColor(R.color.white))
//                cmTextView.setBackgroundResource(R.drawable.bg_common)
//                ftTextView.setTextColor(getColor(R.color.common_color_light))
//                ftTextView.setBackgroundResource(R.drawable.bg_white)
//            }
//            if (weightUnit == "libs"){
//                libsTextView.setTextColor(getColor(R.color.white))
//                libsTextView.setBackgroundResource(R.drawable.bg_common)
//                kgTextView.setTextColor(getColor(R.color.common_color_light))
//                kgTextView.setBackgroundResource(R.drawable.bg_white)
//            }else{
//                kgTextView.setTextColor(getColor(R.color.white))
//                kgTextView.setBackgroundResource(R.drawable.bg_common)
//                libsTextView.setTextColor(getColor(R.color.common_color_light))
//                libsTextView.setBackgroundResource(R.drawable.bg_white)
//            }



            blog.setOnClickListener {
                getCurrentActiveIndicator(this@DashboardAct, "blog", bmiCard, bmrCard, blogCard, bmi, bmr, blog)

                startActivity(Intent(this@DashboardAct, BlogAct::class.java))

            }

        }
    }
    override fun onStart() {
        super.onStart()

        getCurrentActiveIndicator(
            this@DashboardAct,
            "bmi",
            binding.bmiCard,
            binding.bmrCard,
            binding.blogCard,
            binding.bmi,
            binding.bmr,
            binding.blog
        )
    }
}