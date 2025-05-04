package id.herdroid.ecommerce.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val token: String? = null,
    val errorMessage: String? = null
)
