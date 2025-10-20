package com.example.bmilearningproject.util

import android.app.Activity
import android.widget.TextView
import com.example.bmilearningproject.R

fun getWeightUnit(context: Activity, weightUnit: String?, libsTextView: TextView, kgTextView: TextView){

    if (weightUnit == "libs"){
        libsTextView.setTextColor(context.getColor(R.color.white))
        libsTextView.setBackgroundResource(R.drawable.bg_common)
        kgTextView.setTextColor(context.getColor(R.color.common_color_light))
        kgTextView.setBackgroundResource(R.drawable.bg_white)
    }else{
        kgTextView.setTextColor(context.getColor(R.color.white))
        kgTextView.setBackgroundResource(R.drawable.bg_common)
        libsTextView.setTextColor(context.getColor(R.color.common_color_light))
        libsTextView.setBackgroundResource(R.drawable.bg_white)
    }
}

fun getHeightUnit(context: Activity, unit: String?, ftTextView: TextView, cmTextView: TextView){
    if (unit == "ft"){
        cmTextView.setTextColor(context.getColor(R.color.common_color_light))
        cmTextView.setBackgroundResource(R.drawable.bg_white)
        ftTextView.setTextColor(context.getColor(R.color.white))
        ftTextView.setBackgroundResource(R.drawable.bg_common)
    }else{
        cmTextView.setTextColor(context.getColor(R.color.white))
        cmTextView.setBackgroundResource(R.drawable.bg_common)
        ftTextView.setTextColor(context.getColor(R.color.common_color_light))
        ftTextView.setBackgroundResource(R.drawable.bg_white)
    }
}