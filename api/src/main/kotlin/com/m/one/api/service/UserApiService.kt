package com.m.one.api.service

import com.m.one.domain.service.user.UserService
import com.m.one.external.salt.SaltApi
import com.m.one.message.salt.SaltRequest
import com.m.one.message.token.UserToken
import com.m.one.message.user.UserResponse
import mu.KLogging
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import java.lang.RuntimeException

@Service
class UserApiService(
    private val userService: UserService,
    mAuthService: Retrofit,
) {

    private val saltApi = mAuthService.create(SaltApi::class.java)

    companion object : KLogging() {
        const val SALT_ERR = "salt not make"
    }

    fun reg(email: String, password: String): UserResponse {
        val user = userService.insert(email, password)
        saltApi.reg(SaltRequest(user.id!!)).execute().body()
        return user
    }

}