package com.example.microeducation.utlis

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.microeducation.model.Module
import com.example.microeducation.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.Type
import java.util.Arrays


object ApiManager {
    val TAG = "MyApiManager"
    val BASE_URL = "http://172.28.20.216:5132"

    suspend fun loginUser(activity: Activity, name: String, pass: String): String? {
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/api/Auth/login"
            val json = """
                {
                    "name": "$name",
                    "pass": "$pass"
                }
            """.trimIndent()
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull() ?: return@withContext null
            val requestBody = json.toRequestBody(mediaType)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            try {
                val response = client.newCall(request).execute()
                Log.d(TAG, response.body.toString())
                if (!response.isSuccessful) {
                    Toast.makeText(activity, "Неожиданная ошибка ${response.code}", Toast.LENGTH_LONG).show()
                    return@withContext null
                }
                val responseBody = response.body?.string() ?: return@withContext null
                return@withContext responseBody
            } catch (e: Exception) {
                Toast.makeText(activity, "Ошибка подключения к серверу ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }
    }

    suspend fun registerUser(activity: Activity, name: String, mail: String, password: String): Boolean? {
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/api/Auth/register"

            val json = """
                {
                  "name": "$name",
                  "mail": "$mail",
                  "itmo_Id": 0,
                  "itmo_Password": "0",
                  "password_HK": "$password"
                }
            """.trimIndent()

            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull() ?: return@withContext null
            val requestBody = json.toRequestBody(mediaType)
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
            try {
                val response = client.newCall(request).execute()
                return@withContext response.isSuccessful
            } catch (e: Exception) {
                Toast.makeText(activity, "Ошибка подключения к серверу ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }
    }

    suspend fun getUser(activity: Activity, jwtToken: String): User? {
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/api/Auth/GetUserByToken"

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = "\"$jwtToken\"".toRequestBody(mediaType)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Toast.makeText(activity, "Неожиданная ошибка ${response.code}", Toast.LENGTH_LONG).show()
                    return@withContext null
                }
                val gson = Gson()
                val responseBody = gson.fromJson(response.body?.string(), User::class.java) ?: return@withContext null
                return@withContext responseBody
            } catch (e: Exception) {
                Toast.makeText(activity, "Ошибка подключения к серверу ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }
    }

    suspend fun getModules(activity: Activity, courseName: String): List<Module>? {
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/api/Module/GetModules"

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = "\"$courseName\"".toRequestBody(mediaType)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Toast.makeText(activity, "Неожиданная ошибка ${response.code}", Toast.LENGTH_LONG).show()
                    return@withContext null
                }
                val gson = Gson()
                val responseBody = gson.fromJson(response.body?.string(), Array<Module>::class.java).toList()
                return@withContext responseBody
            } catch (e: Exception) {
                Toast.makeText(activity, "Ошибка подключения к серверу ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }
    }

    private fun <T> typeOfList(): Type {
        return object : TypeToken<List<T>>() {}.type
    }
}
