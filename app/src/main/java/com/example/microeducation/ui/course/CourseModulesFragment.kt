package com.example.microeducation.ui.course

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.example.microeducation.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CourseModulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseModulesFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_modules, container, false)

        val modulesLayout = view.findViewById<LinearLayout>(R.id.modulesLayout)

        val listOfModules = (activity as CourseActivity).listOfModules
        for (module in listOfModules){
            val moduleButton = createModuleButton(module.name)
            modulesLayout.addView(moduleButton)

            moduleButton.setOnClickListener {
                val intent = Intent(activity, VideoActivity::class.java)
                intent.putExtra("url", module.videoLink)
                startActivity(intent)
            }
        }
        return view
    }

    private fun createModuleButton(moduleName: String): Button {
        val button = Button(activity)

        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        button.setBackgroundResource(typedValue.resourceId)

        button.minWidth = 0
        button.minHeight = 0
        button.textAlignment = TEXT_ALIGNMENT_TEXT_START
        button.text = moduleName
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        button.isAllCaps = false
        return button
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CourseModuelsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseModulesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}