import android.content.Context
import android.content.SharedPreferences

class UserSessionManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "USER_SESSION_PREFERENCES"
        private const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val KEY_JWT_TOKEN = "JWT_TOKEN"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Сохранение статуса сессии
    fun setLoggedIn(isLoggedIn: Boolean) {
        with(prefs.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    // Получение статуса сессии
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    // Сохранение JWT-токена
    fun setJwtToken(jwtToken: String?) {
        with(prefs.edit()) {
            putString(KEY_JWT_TOKEN, jwtToken)
            apply()
        }
    }

    // Получение JWT-токена
    fun getJwtToken(): String? = prefs.getString(KEY_JWT_TOKEN, null)

    // Очистка всех сохраненных данных
    fun clearAllData() {
        with(prefs.edit()) {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_JWT_TOKEN)
            apply()
        }
    }
}