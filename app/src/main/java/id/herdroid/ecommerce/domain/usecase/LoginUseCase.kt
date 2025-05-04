package id.herdroid.ecommerce.domain.usecase

import id.herdroid.ecommerce.domain.model.LoginRequest
import id.herdroid.ecommerce.domain.model.LoginResponse
import id.herdroid.ecommerce.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest): LoginResponse {
        return repository.login(request)
    }
}
