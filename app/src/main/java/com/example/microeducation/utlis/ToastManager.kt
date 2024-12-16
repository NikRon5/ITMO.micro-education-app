package com.example.microeducation.utlis

import android.app.Activity
import android.content.Context
import android.widget.Toast

class ToastManager(private val context: Context) {
    fun inDevelopment() {
        Toast.makeText(
            context,
            "Эта функция пока в разработке",
            Toast.LENGTH_LONG
        ).show()
    }
}