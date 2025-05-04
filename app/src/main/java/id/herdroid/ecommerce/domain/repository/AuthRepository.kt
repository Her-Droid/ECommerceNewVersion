package id.herdroid.ecommerce.domain.repository

import id.herdroid.ecommerce.domain.model.LoginRequest
import id.herdroid.ecommerce.domain.model.LoginResponse

interface AuthRepository {
    suspend fun login(request: LoginRequest): LoginResponse
}
