package com.example.microeducation.main

import UserSessionManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.microeducation.ui.auth.LoginActivity
import com.example.microeducation.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userSessionManager = UserSessionManager(applicationContext)
        val isLogged = userSessionManager.isLoggedIn()
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