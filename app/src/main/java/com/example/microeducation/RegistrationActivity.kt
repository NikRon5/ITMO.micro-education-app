package com.example.microeducation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registration)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun goToLogin(view: View) {
        startActivity(
            Intent(
                this@RegistrationActivity,
                LoginActivity::class.java
            )
        )
        finish()
    }

    fun itmoIdLogin(view: View) {
        Utils.functionIsNotAccessibleToast(this)
    }

    fun register(view: View) {
        Variables.isLogged = true
        startActivity(
            Intent(
                this@RegistrationActivity,
                HomeActivity::class.java
            )
        )
        finish()
    }
}