package com.m.one.external.salt

import com.m.one.message.salt.SaltRequest
import com.m.one.message.salt.SaltResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SaltApi {

    @POST("/internal/salt")
    fun reg(@Body saltRequest: SaltRequest): Call<SaltResponse>

}