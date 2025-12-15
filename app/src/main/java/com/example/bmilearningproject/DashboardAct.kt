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
        val allPrefs = pref.all
        Log.d("PREFS_DUMP", allPrefs.toString())
        var gender = pref.getString(Constant.genderKey, "male")
        var heightUnitCode = pref.getString(Constant.heightUnitKey, "ft")
        var heightValueCode = pref.getFloat(Constant.heightValueKey, 5.7f)
        var weightUnitValue = pref.getString(Constant.weightUnitKey, "lbs")
        var weightValue = pref.getFloat(Constant.weightValueKey, 165f)
        var ageUnit = pref.getString(Constant.ageUnitKey, "y")
        var ageValue = pref.getFloat(Constant.ageValueKey, 25f)
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
            heightValueView.showSoftInputOnFocus = false
            heightValueView.isFocusable = false
            heightValueView.isCursorVisible = false
            heightValueView.setOnClickListener {
                showHeightDialog()
            }
            getWeightUnit(this@DashboardAct, weightUnitValue, libsTextView, kgTextView)
            kgTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "kg", libsTextView, kgTextView)
                if (weightUnitValue == "lbs") {
                    weightValue = getWeightInKg(weightValue)
                    weightUnitValue = "kg"
                    weightValueView.setText(String.format("%.2f", weightValue))
                    weightUnit.hint = "Weight (kg)"
                    editor.putFloat(Constant.weightValueKey, weightValue)
                    editor.putString(Constant.weightUnitKey, "kg")
                    editor.apply()
                    Log.i(TAG, "converted weight: $weightValue")
                }
            }
            libsTextView.setOnClickListener {
                getWeightUnit(this@DashboardAct, "libs", libsTextView, kgTextView)
                if (weightUnitValue == "kg"){
                    weightValue = getWeightInLbs(weightValue)
                    weightValueView.setText(String.format("%.2f", weightValue))
                    weightUnitValue = "lbs"
                    weightUnit.hint = "Weight (lbs)"
                    editor.putFloat(Constant.weightValueKey, weightValue)
                    editor.putString(Constant.weightUnitKey, "lbs")
                    editor.apply()
                    Log.i(TAG, "converted weight: $weightValue")
                }
            }
            getHeightUnit(this@DashboardAct, heightUnitCode, ftTextView, cmTextView)
            ftTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "ft", ftTextView, cmTextView)
                if (heightUnitCode  == "cm"){
                    heightValueCode = getHeightInFt(heightValueCode)
                    heightValueView.setText(String.format("%.2f", heightValueCode))
                    heightUnit.hint = "Height (ft)"
                    heightUnitCode = "ft"
                    editor.putFloat(Constant.heightValueKey, heightValueCode)
                    editor.putString(Constant.heightUnitKey, heightUnitCode)
                    editor.apply()
                }
            }
            cmTextView.setOnClickListener {
                getHeightUnit(this@DashboardAct, "cm", ftTextView, cmTextView)
                if (heightUnitCode == "ft"){

                    heightValueCode = getHeightInCm(heightValueCode)
                    heightValueView.setText(String.format("%.2f", heightValueCode))
                    heightUnit.hint = "Height (cm)"
                    heightUnitCode = "cm"
                    editor.putFloat(Constant.heightValueKey, heightValueCode)
                    editor.putString(Constant.heightUnitKey, heightUnitCode)
                    editor.apply()
                }
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
            .setMessage("Are you sure you want to edit Weight")
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
    private fun showHeightDialog() {
        AlertDialog.Builder(this)
            .setTitle("Edit")
            .setMessage("Are you sure you want to edit Height")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                Intent(this, HeightAct::class.java).also {
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
