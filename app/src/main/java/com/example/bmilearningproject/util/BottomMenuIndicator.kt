package com.example.bmilearningproject.util

import android.app.Activity
import android.graphics.PorterDuff
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.bmilearningproject.R
import com.google.android.material.card.MaterialCardView

fun getCurrentActiveIndicator(context: Activity,active: String, bmiCard: MaterialCardView, bmrCard: MaterialCardView, blogCard: MaterialCardView,
    bmi: ImageView, bmr: ImageView, blog: ImageView
    ){
    val white = ContextCompat.getColor(context, R.color.white)
    Log.i("TAG", "getCurrentActiveIndicator: Active Menu is: " + active)
    if (active == "bmi"){
        Log.i("TAG", "getCurrentActiveIndicator: BMI")
        bmi.setColorFilter(white, PorterDuff.Mode.SRC_IN)
        bmiCard.setCardBackgroundColor(context.getColor(R.color.common_color))

        bmrCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        bmr.setColorFilter(null)

        blogCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        blog.setColorFilter(null)

    }
    if(active == "bmr"){
        Log.i("TAG", "getCurrentActiveIndicator: BMR")
        bmr.setColorFilter(white, PorterDuff.Mode.SRC_IN)
        bmrCard.setCardBackgroundColor(context.getColor(R.color.common_color))

        bmiCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        bmi.setColorFilter(null)
        blogCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        blog.setColorFilter(null)

    }
    if(active == "blog"){
        Log.i("TAG", "getCurrentActiveIndicator: Blog")
        blog.setColorFilter(white, PorterDuff.Mode.SRC_IN)
        blogCard.setCardBackgroundColor(context.getColor(R.color.common_color))

        bmiCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        bmi.setColorFilter(null)
        bmrCard.setCardBackgroundColor(context.getColor(R.color.transparent))
        bmr.setColorFilter(null)
    }
}