package com.m.one.message.salt

data class SaltResponse(
    var id: String,
    var email: String,
    var salt: String,
)
