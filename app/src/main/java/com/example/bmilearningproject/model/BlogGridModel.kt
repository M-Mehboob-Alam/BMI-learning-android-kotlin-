package com.example.bmilearningproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlogGridModel(var mainHeading: String, var  time : String? = "4 min" , var  des : String? = "Description" ): Parcelable