package com.example.microeducation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.microeducation.R
import com.example.microeducation.ui.course.CourseActivity
import com.example.microeducation.utlis.ApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoursesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_courses, container, false)
        val coursesCardsLayout = view.findViewById<LinearLayout>(R.id.coursesCards)

        // Importing courses
        lifecycleScope.launch {
            try {
                val courses = ApiManager.getCourses(requireActivity())
                withContext(Dispatchers.Main) {
                    if (courses != null) {
                        var counter = 0
                        for (course in courses){
                            val courseLine = LayoutInflater.from(activity).inflate(R.layout.course_line, null, false)
                            val courseButton = createCourseCardButton(course, counter)

                            courseButton.setOnClickListener {
                                val intent = Intent(activity, CourseActivity::class.java)
                                intent.putExtra("courseName", course)
                                startActivity(intent)
                            }
                            coursesCardsLayout.addView(courseButton)
                            coursesCardsLayout.addView(courseLine)
                            counter += 1
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Ошибка получения данных о курсах", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireActivity(), "Ошибка получения данных о курсах", Toast.LENGTH_LONG).show()
                }
            }
        }
        return view
    }

    private fun createCourseCardButton(courseName: String, tag: Int): Button {
        val button = Button(activity)
        val scale: Float = activity?.resources?.displayMetrics?.density ?: 1f;

        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(androidx.appcompat.R.attr.selectableItemBackground, typedValue, true)
        button.setBackgroundResource(typedValue.resourceId)

        button.tag = tag
        button.height = (64 * scale + 0.5f).toInt()
        button.width = ViewGroup.LayoutParams.MATCH_PARENT
        button.text = courseName
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        if (courseName == "Python")
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_python,0, 0, 0)
        else
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_course_default_48,0, 0, 0)
        button.isAllCaps = false
        button.compoundDrawablePadding = (-48 * scale + 0.5f).toInt()
        return button
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}