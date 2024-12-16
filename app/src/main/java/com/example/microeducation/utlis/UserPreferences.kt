import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_NAME = "name"
        private const val KEY_MAIL = "mail"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var userId: Int
        get() = preferences.getInt(KEY_USER_ID, -1)
        set(value) = preferences.edit().putInt(KEY_USER_ID, value).apply()

    var name: String?
        get() = preferences.getString(KEY_NAME, null)
        set(value) = preferences.edit().putString(KEY_NAME, value).apply()

    var mail: String?
        get() = preferences.getString(KEY_MAIL, null)
        set(value) = preferences.edit().putString(KEY_MAIL, value).apply()

    fun clear() {
        preferences.edit().clear().apply()
    }
}