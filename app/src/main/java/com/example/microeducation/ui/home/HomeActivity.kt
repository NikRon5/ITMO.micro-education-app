package com.example.microeducation.ui.home

import UserPreferences
import UserSessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.microeducation.R
import com.example.microeducation.utlis.ApiManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var userSessionManager: UserSessionManager
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadFragment(CoursesFragment())

        userSessionManager = UserSessionManager(applicationContext)
        userPreferences = UserPreferences(applicationContext)

        lifecycleScope.launch {
            try {
                val jwtToken = userSessionManager.getJwtToken()
                if (jwtToken != null) {
                    val user = ApiManager.getUser(this@HomeActivity, jwtToken)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            userPreferences.userId = user.id
                            userPreferences.name = user.name
                            userPreferences.mail = user.mail
                        } else {
                            Toast.makeText(this@HomeActivity, "Ошибка получения данных о пользователе!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HomeActivity, "Ошибка получения данных о пользователе", Toast.LENGTH_LONG).show()
                }
            }
        }

        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(CoursesFragment())
                    true
                }
                R.id.account -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
//        lifecycleScope.launch {
//            ApiManager.getModulesAsync("Python").collectLatest { modules ->
//                if (modules != null) {
//                    // Обрабатываем успешный результат
//                    for (module in modules) {
//                        Log.d("TAG", "Модуль: ${module.name}")
//                    }
//                } else {
//                    // Обрабатываем ошибку
//                    Toast.makeText(this@HomeActivity, "Произошла ошибка при загрузке модулей.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer,fragment)
        transaction.commit()
    }
}