package com.example.microeducation.ui.auth

import UserSessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.microeducation.R
import com.example.microeducation.utlis.ApiManager
import com.example.microeducation.ui.home.HomeActivity
import com.example.microeducation.utlis.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private lateinit var userSessionManager: UserSessionManager
    private lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toastManager = ToastManager(applicationContext)
        userSessionManager = UserSessionManager(applicationContext)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val itmoIdLoginButton = findViewById<Button>(R.id.itmoIdLoginButton)
        val toRegistrationButton = findViewById<TextView>(R.id.toRegistrationButton)

        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameLogin).text.toString()
            val password = findViewById<EditText>(R.id.passwordLogin).text.toString()
            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this@LoginActivity, "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG).show()
            }
            else {
                lifecycleScope.launch {
                    try {
                        val token = ApiManager.loginUser(this@LoginActivity,username, password)

                        withContext(Dispatchers.Main) {
                            if (token != null) {
                                userSessionManager.setJwtToken(token)
                                userSessionManager.setLoggedIn(true)

                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        HomeActivity::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Ошибка входа!", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Ошибка входа!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        itmoIdLoginButton.setOnClickListener {
            toastManager.inDevelopment()
        }


        toRegistrationButton.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegistrationActivity::class.java
                )
            )
            finish()
        }
    }
}