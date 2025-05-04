package id.herdroid.ecommerce.data.remote.api

import id.herdroid.ecommerce.domain.model.LoginRequest
import id.herdroid.ecommerce.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
