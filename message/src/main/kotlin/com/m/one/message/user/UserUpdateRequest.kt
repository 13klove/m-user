package com.m.one.message.user

data class UserUpdateRequest(
    var email: String,
    var password: String,
    var newPassword: String
)
