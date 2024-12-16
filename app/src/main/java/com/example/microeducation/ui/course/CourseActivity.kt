package com.example.microeducation.ui.course

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.microeducation.R

class CourseActivity : AppCompatActivity() {
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

        val modulesButton = findViewById<TextView>(R.id.modulesButton)
        val testsButton = findViewById<TextView>(R.id.testsButton)
        val progressButton = findViewById<TextView>(R.id.progressButton)
        val descButton = findViewById<TextView>(R.id.descButton)


        progressButton.setOnClickListener {
            loadFragment(CourseProgressFragment())

            progressButton.setBackgroundResource(R.color.more_dark_gray)
            modulesButton.setBackgroundResource(R.color.transparent)
            testsButton.setBackgroundResource(R.color.transparent)
            descButton.setBackgroundResource(R.color.transparent)

        }

        modulesButton.setOnClickListener {
            loadFragment(CourseModulesFragment())

            modulesButton.setBackgroundResource(R.color.more_dark_gray)
            progressButton.setBackgroundResource(R.color.transparent)
            testsButton.setBackgroundResource(R.color.transparent)
            descButton.setBackgroundResource(R.color.transparent)
        }

        testsButton.setOnClickListener {
            loadFragment(CourseTestsFragment())

            testsButton.setBackgroundResource(R.color.more_dark_gray)
            progressButton.setBackgroundResource(R.color.transparent)
            modulesButton.setBackgroundResource(R.color.transparent)
            descButton.setBackgroundResource(R.color.transparent)

        }

        descButton.setOnClickListener {
            loadFragment(CourseDescFragment())

            descButton.setBackgroundResource(R.color.more_dark_gray)
            progressButton.setBackgroundResource(R.color.transparent)
            modulesButton.setBackgroundResource(R.color.transparent)
            testsButton.setBackgroundResource(R.color.transparent)
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