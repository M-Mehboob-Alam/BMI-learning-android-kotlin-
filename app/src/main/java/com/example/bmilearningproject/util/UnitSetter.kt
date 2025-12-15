package com.example.bmilearningproject.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView
import com.example.bmilearningproject.R

lateinit var pref : SharedPreferences
//val pref = getSharedPreferences(Constant.sharedPrefKey, Context.MODE_PRIVATE)
fun initPrefs(context: Context) {
    pref = context.getSharedPreferences(Constant.sharedPrefKey, Context.MODE_PRIVATE)
}


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

fun Activity.getCurrentHeightUnit(): Pair<String, Float>{
    val currentHeightValue = pref.getFloat(Constant.heightValueKey, 0.0f)
    val currentHeightUnit = pref.getString(Constant.heightUnitKey, "cm") ?: "cm"
    return currentHeightUnit to currentHeightValue
}
fun Activity.getCurrentWeightUnit() : Pair<String, Float>{
    val currentWeightValue = pref.getFloat(Constant.weightValueKey, 0.0f)
    val currentWeightUnit = pref.getString(Constant.weightUnitKey, "kg") ?: "kg"
    return currentWeightUnit to currentWeightValue
}
private const val POUNDS_PER_KG = 2.20462f
private const val CM_PER_FOOT = 30.48f

private const val CM_PER_INCH = 2.54f

private  const val  INCH_PER_FOOT = 12f

fun Activity.getWeightInKg(lbs: Float): Float {
    return lbs / POUNDS_PER_KG
}

fun Activity.getWeightInLbs(kg: Float): Float {
    return kg * POUNDS_PER_KG
}

fun Activity.getHeightInFt(cm: Float): Float {
    return cm / CM_PER_FOOT
}

fun Activity.getHeightInCm(ft: Float): Float {
    return ft * CM_PER_FOOT
}
