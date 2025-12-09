package com.example.bmilearningproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmilearningproject.databinding.ActivityGenderBinding
import com.example.bmilearningproject.util.Constant

class GenderAct : AppCompatActivity() {
    private var TAG = "GenderAct"
    private  lateinit var binding : ActivityGenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenderBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = getSharedPreferences("BmiCalculator", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(Constant.genderKey, "male")
        editor.apply()

        binding.apply {
            genderMale.setOnClickListener {
                changeGenderCardColor("male")
                editor.putString(Constant.genderKey, "male")
                editor.apply()
            }
            genderFemale.setOnClickListener {
                changeGenderCardColor("female")
                editor.putString(Constant.genderKey, "female")
                editor.apply()
            }
            nextBtn.setOnClickListener {
                startActivity(Intent(this@GenderAct, AgeAct::class.java))
            }
        }
    }

    fun changeGenderCardColor(gender:String){
        if (gender == "male"){
            binding.genderMale.setCardBackgroundColor(getColor(R.color.common_color))
            binding.genderFemale.setCardBackgroundColor(getColor(R.color.card_color))
            binding.male.setTextColor(getColor(R.color.white))
            binding.female.setTextColor(getColor(R.color.common_color_light))
        }else{
            binding.genderFemale.setCardBackgroundColor(getColor(R.color.common_color))
            binding.genderMale.setCardBackgroundColor(getColor(R.color.card_color))
            binding.female.setTextColor(getColor(R.color.white))
            binding.male.setTextColor(getColor(R.color.common_color_light))
        }
    }

}