package com.m.one.api.service

import com.m.one.domain.service.user.UserService
import com.m.one.external.token.TokenApi
import com.m.one.message.salt.SaltRequest
import com.m.one.message.token.TokenRequest
import com.m.one.message.token.UserToken
import org.springframework.stereotype.Service
import retrofit2.Retrofit

@Service
class LoginService(
    private val userService: UserService,
    mAuthService: Retrofit,
) {

    private val tokenApi = mAuthService.create(TokenApi::class.java)

    fun signUp(email: String, password: String): UserToken {
        val user = userService.getUser(email, password)
        with(user) {
            val token = tokenApi.getToken(TokenRequest(id!!, email)).execute().body()

            return UserToken(
                token!!.baseToken.token,
                token.refreshToken.token
            )
        }
    }

}