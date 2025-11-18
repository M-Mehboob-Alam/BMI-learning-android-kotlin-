package com.example.bmilearningproject.callback

import android.view.View
import com.example.bmilearningproject.model.BlogTopModel

interface BlogTopMenuListener {
    fun onBlogTopMenuClick(model : BlogTopModel, text: View)
}