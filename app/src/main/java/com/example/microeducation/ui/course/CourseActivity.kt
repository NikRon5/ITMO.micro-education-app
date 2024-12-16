package com.example.microeducation.ui.course

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.microeducation.R
import com.example.microeducation.model.Module
import com.example.microeducation.utlis.ApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class CourseActivity : AppCompatActivity() {

    lateinit var listOfModules : List<Module>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.course)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadFragment(CourseProgressFragment())

        val courseName = intent.getStringExtra("courseName")
        val courseTitle = findViewById<TextView>(R.id.courseTitle)
        courseTitle.text = courseName

        lifecycleScope.launch {
            try {
                val modules = ApiManager.getModules(this@CourseActivity, courseName.toString())
                withContext(Dispatchers.Main) {
                    if (modules != null) {
                        listOfModules = modules
                    } else {
                        Toast.makeText(this@CourseActivity, "Ошибка информации о модулях", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CourseActivity, "Ошибка информации о модулях", Toast.LENGTH_LONG).show()
                }
            }
        }

        val modulesButton = findViewById<TextView>(R.id.modulesButton)
        val testsButton = findViewById<TextView>(R.id.testsButton)
        val progressButton = findViewById<TextView>(R.id.progressButton)


        progressButton.setOnClickListener {
            loadFragment(CourseProgressFragment())

            progressButton.setBackgroundResource(R.color.more_dark_gray)
            modulesButton.setBackgroundResource(R.color.transparent)
            testsButton.setBackgroundResource(R.color.transparent)
        }

        modulesButton.setOnClickListener {
            loadFragment(CourseModulesFragment())

            modulesButton.setBackgroundResource(R.color.more_dark_gray)
            progressButton.setBackgroundResource(R.color.transparent)
            testsButton.setBackgroundResource(R.color.transparent)
        }

        testsButton.setOnClickListener {
            loadFragment(CourseTestsFragment())

            testsButton.setBackgroundResource(R.color.more_dark_gray)
            progressButton.setBackgroundResource(R.color.transparent)
            modulesButton.setBackgroundResource(R.color.transparent)
        }
    }

    fun goBack(view: View) {
        finish()
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.courseContainer,fragment)
        transaction.commit()
    }
}