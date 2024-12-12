package com.example.microeducation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun goToRegistration(view: View) {
        startActivity(
            Intent(
                this@LoginActivity,
                RegistrationActivity::class.java
            )
        )
        finish()
    }

    fun login(view: View) {
        Variables.isLogged = true
        startActivity(
            Intent(
                this@LoginActivity,
                HomeActivity::class.java
            )
        )
        finish()
    }

    fun itmoIdLogin(view: View) {
        Utils.functionIsNotAccessibleToast(this)
    }
}