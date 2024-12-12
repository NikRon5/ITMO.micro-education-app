package com.example.microeducation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val isLogged: Boolean = Variables.isLogged
        if (!isLogged) {
            startActivity(
                Intent(
                    this@MainActivity,
                    LoginActivity::class.java
                )
            )
            finish()
        }
        else {
            startActivity(
                Intent(
                    this@MainActivity,
                    HomeActivity::class.java
                )
            )
            finish()
        }
    }
}