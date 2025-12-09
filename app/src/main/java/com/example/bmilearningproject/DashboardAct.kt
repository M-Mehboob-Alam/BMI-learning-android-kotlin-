package com.example.bmilearningproject

import android.app.AlertDialog
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
import com.example.bmilearningproject.util.getHeightInCm
import com.example.bmilearningproject.util.getHeightInFt
import com.example.bmilearningproject.util.getHeightUnit
import com.example.bmilearningproject.util.getWeightInKg
import com.example.bmilearningproject.util.getWeightInLbs
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
        val heightUnitCode = pref.getString("heightUnit", "cm")
        val heightValueCode = pref.getFloat("heightValue", 0f)
        val weightUnitValue = pref.getString("weightUnit", "kg")
        val weightValue = pref.getFloat("weightValue", 0f)
        val ageUnit = pref.getString("ageUnit", "y")
        val ageValue = pref.getFloat("ageValue", 0f)
        val isCompletedSetting = pref.getBoolean(Constant.isCompletedSettingKey, false)
        if (!isCompletedSetting) {
            editor.putBoolean(Constant.isCompletedSettingKey, true)
            editor.apply()
        }
//        Log.i(TAG, "Gender: $gender  Height: $heightUnitCode $heightValueCode Weight: $weightUnitValue $weightValue Age: $ageUnit $ageValue isCompletedSetting: $isCompletedSetting")
        binding.apply {
            nextBtn.setOnClickListener {
                val intent = Intent(this@DashboardAct, ResultAct::class.java)
                intent.putExtra("name", "BMI")
                startActivity(intent)
            }
            settingIc.setOnClickListener {
                startActivity(Intent(this@DashboardAct, SettingAct::class.java))
                finish()
            }
            if (gender == "female") {
//                Log.i(TAG, "onCreate: gender is female selected")
//                Toast.makeText(this@DashboardAct, "selected gender is female", Toast.LENGTH_SHORT).show()
                genderFemale.setCardBackgroundColor(getColor(R.color.common_color))
                genderMale.setCardBackgroundColor(getColor(R.color.card_color))
                female.setTextColor(getColor(R.color.white))
                male.setTextColor(getColor(R.color.common_color_light))
            } else {
                genderMale.setCardBackgroundColor(getColor(R.color.common_color))
                genderFemale.setCardBackgroundColor(getColor(R.color.card_color))
                male.setTextColor(getColor(R.color.white))
                female.setTextColor(getColor(R.color.common_color_light))

            }
            ageValueView.setText(String.format("%.2f", ageValue))
            weightValueView.setText(String.format("%.2f", weightValue))
            weightValueView.showSoftInputOnFocus = false
            weightValueView.isFocusable = false
            weightValueView.isCursorVisible = false
            weightValueView.setOnClickListener {
                showWeightDialog()
            }
            heightValueView.setText(String.format("%.2f", heightValueCode))
            getWeightUnit(this@DashboardAct, weightUnitValue, libsTextView, kgTextView)
            kgTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "kg", libsTextView, kgTextView)
                val convertedWeightKg = getWeightInKg(weightValue)
                weightValueView.setText(String.format("%.2f", convertedWeightKg))
                weightUnit.hint = "Weight (kg)"
                Log.i(TAG, "converted weight: $convertedWeightKg")
            }
            libsTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "libs", libsTextView, kgTextView)
                val convertedWeight = getWeightInLbs(weightValue)
                weightValueView.setText(String.format("%.2f", convertedWeight))
                weightUnit.hint = "Weight (lbs)"
                Log.i(TAG, "converted weight: $convertedWeight")
            }
            getHeightUnit(this@DashboardAct, heightUnitCode, ftTextView, cmTextView)
            ftTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "ft", ftTextView, cmTextView)
                val convertedHeightFt = getHeightInFt(heightValueCode)
                heightValueView.setText(String.format("%.2f", convertedHeightFt))
                heightUnit.hint = "Height (ft)"
            }
            cmTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "cm", ftTextView, cmTextView)
                val convertedHeightCm = getHeightInCm(heightValueCode)
                heightValueView.setText(String.format("%.2f", convertedHeightCm))
                heightUnit.hint = "Height (cm)"
            }
            bmr.setOnClickListener {
                getCurrentActiveIndicator(
                    this@DashboardAct,
                    "bmr",
                    bmiCard,
                    bmrCard,
                    blogCard,
                    bmi,
                    bmr,
                    blog
                )

                startActivity(Intent(this@DashboardAct, BmrAct::class.java))
            }
            bmi.setOnClickListener {
                getCurrentActiveIndicator(
                    this@DashboardAct,
                    "bmi",
                    bmiCard,
                    bmrCard,
                    blogCard,
                    bmi,
                    bmr,
                    blog
                )
                startActivity(Intent(this@DashboardAct, BlogAct::class.java))
            }

            blog.setOnClickListener {
                getCurrentActiveIndicator(
                    this@DashboardAct,
                    "blog",
                    bmiCard,
                    bmrCard,
                    blogCard,
                    bmi,
                    bmr,
                    blog
                )
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

    private fun showWeightDialog() {
        AlertDialog.Builder(this)
            .setTitle("Edit")
            .setMessage("Are you sure you want to edit this field")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                Intent(this, WeightActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }
}