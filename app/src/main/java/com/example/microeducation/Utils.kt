package com.example.microeducation

import android.app.Activity
import android.widget.Toast

class Utils {
    companion object {
        fun functionIsNotAccessibleToast(activity: Activity) {
            Toast.makeText(
                activity,
                "Эта функция пока в разработке",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}