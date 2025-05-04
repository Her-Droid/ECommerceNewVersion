package id.herdroid.ecommerce.data.repository


import id.herdroid.ecommerce.data.remote.api.AuthApi
import id.herdroid.ecommerce.domain.model.LoginRequest
import id.herdroid.ecommerce.domain.model.LoginResponse
import id.herdroid.ecommerce.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }
}
