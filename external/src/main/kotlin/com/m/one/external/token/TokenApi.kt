package com.m.one.external.token

import com.m.one.message.token.PairTokenResponse
import com.m.one.message.token.TokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {

    @POST("/internal/token")
    fun getToken(
        @Body tokenRequest: TokenRequest
    ): Call<PairTokenResponse>

}