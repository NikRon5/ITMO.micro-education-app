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

class RegistrationActivity : AppCompatActivity() {
    private lateinit var userSessionManager: UserSessionManager
    private lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registration)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toastManager = ToastManager(applicationContext)
        userSessionManager = UserSessionManager(applicationContext)

        val registrationButton = findViewById<Button>(R.id.registrationButton)
        val itmoIdRegistrationButton = findViewById<Button>(R.id.itmoIdRegistrationButton)
        val toLoginButton = findViewById<TextView>(R.id.toLoginButton)

        registrationButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameRegistration).text.toString()
            val mail = findViewById<EditText>(R.id.mailRegistration).text.toString()
            val password = findViewById<EditText>(R.id.passwordRegistration).text.toString()
            if (username.isBlank() || mail.isBlank() || password.isBlank()) {
                Toast.makeText(this@RegistrationActivity, "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG).show()
            }
            else {
                lifecycleScope.launch {
                    try {
                        val response = ApiManager.registerUser(this@RegistrationActivity, username, mail, password)
                        Log.d("Registration", response.toString())
                        withContext(Dispatchers.Main) {
                            if (response != null && response) {
                                // userSessionManager.setJwtToken()
                                userSessionManager.setLoggedIn(true)

                                startActivity(
                                    Intent(
                                        this@RegistrationActivity,
                                        HomeActivity::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(this@RegistrationActivity, "Ошибка регистрации!", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegistrationActivity, "Ошибка регистрации!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        itmoIdRegistrationButton.setOnClickListener {
            toastManager.inDevelopment()
        }

        toLoginButton.setOnClickListener {
            startActivity(
                Intent(
                    this@RegistrationActivity,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
}