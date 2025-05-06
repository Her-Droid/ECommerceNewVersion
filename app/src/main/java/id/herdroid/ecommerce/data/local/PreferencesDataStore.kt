package id.herdroid.ecommerce.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "token"
        private const val USERNAME_KEY = "username"
        private const val PROFILE_IMAGE_KEY = "profile_image"
    }

    private val _token = MutableStateFlow(sharedPreferences.getString(TOKEN_KEY, null))
    val token: Flow<String?> = _token.asStateFlow()

    private val _username = MutableStateFlow(sharedPreferences.getString(USERNAME_KEY, null))
    val username: Flow<String?> = _username.asStateFlow()

    private val _profileImage = MutableStateFlow(sharedPreferences.getString(PROFILE_IMAGE_KEY, null))
    val profileImage: Flow<String?> = _profileImage.asStateFlow()

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
        _token.value = token
    }

    fun saveUsername(username: String) {
        sharedPreferences.edit().putString(USERNAME_KEY, username).apply()
        _username.value = username
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
        _token.value = null
    }

    fun clearUsername() {
        sharedPreferences.edit().remove(USERNAME_KEY).apply()
        _username.value = null
    }

    fun saveProfileImage(uri: String) {
        sharedPreferences.edit().putString(PROFILE_IMAGE_KEY, uri).apply()
        _profileImage.value = uri
    }
}
