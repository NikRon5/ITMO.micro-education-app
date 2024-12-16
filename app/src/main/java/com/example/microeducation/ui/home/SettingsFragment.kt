package com.example.microeducation.ui.home

import UserPreferences
import UserSessionManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.microeducation.R
import com.example.microeducation.ui.auth.LoginActivity
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
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var userSessionManager: UserSessionManager

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
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        userSessionManager = UserSessionManager(requireContext())

        lifecycleScope.launch {
            try {
                val jwtToken = userSessionManager.getJwtToken()
                if (jwtToken != null) {
                    val user = ApiManager.getUser(requireActivity(), jwtToken)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            val usernameField = view.findViewById<TextView>(R.id.usernameField)
                            val usermailField = view.findViewById<TextView>(R.id.usermailField)

                            usernameField.text = user.name
                            usermailField.text = user.mail
                        } else {
                            Toast.makeText(requireActivity(), "Ошибка получения данных о пользователе!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireActivity(), "Ошибка получения данных о пользователе", Toast.LENGTH_LONG).show()
                }
            }
        }

        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            userSessionManager.setLoggedIn(false)
            userSessionManager.setJwtToken("")
            startActivity(
                Intent(
                    activity,
                    LoginActivity::class.java
                )
            )
            activity?.finish()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}