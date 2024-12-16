import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class NetworkManager(private val coroutineScope: CoroutineScope) {

    companion object {
        const val TAG = "NetworkManager"
    }

    fun sendRequestToServer(url: String, params: Map<String, Any>) {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val result = performRequest(url, params)
                handleSuccess(result)
            } catch (e: Exception) {
                Log.e(TAG, "Error sending request to server", e)
                handleFailure(e.message ?: "Unknown error occurred")
            }
        }
    }

    private suspend fun performRequest(url: String, params: Map<String, Any>): String {
        val jsonParams = JSONObject(params).toString()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonParams.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val okHttpClient = OkHttpClient()
        val response = okHttpClient.newCall(request).execute()

        return response.body?.string() ?: "{}"
    }

    private fun handleSuccess(result: String) {
        Log.d(TAG, "Request successful: $result")
        // Здесь можно обработать успешный результат
    }

    private fun handleFailure(errorMessage: String) {
        Log.e(TAG, "Request failed: $errorMessage")
        // Здесь можно обработать ошибку
    }
}