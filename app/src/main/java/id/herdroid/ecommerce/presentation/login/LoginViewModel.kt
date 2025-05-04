package id.herdroid.ecommerce.presentation.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.PreferencesDataStore
import id.herdroid.ecommerce.domain.model.LoginRequest
import id.herdroid.ecommerce.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState
    val token = preferencesDataStore.token
    fun login(username: String, password: String) {
        _uiState.value = LoginState(isLoading = true)

        viewModelScope.launch {
            try {
                val response = loginUseCase(LoginRequest(username, password))
                preferencesDataStore.saveToken(response.token)
                preferencesDataStore.saveUsername(username)
                _uiState.value = LoginState(
                    isLoading = false,
                    isSuccess = true,
                    token = response.token
                )
            } catch (e: Exception) {
                _uiState.value = LoginState(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = e.message
                )
            }
        }

    }
}
