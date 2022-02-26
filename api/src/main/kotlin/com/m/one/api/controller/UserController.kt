package com.m.one.api.controller

import com.m.one.api.controller.UserController.Companion.BASE_URL
import com.m.one.api.service.LoginService
import com.m.one.api.service.UserApiService
import com.m.one.domain.service.user.UserService
import com.m.one.message.token.UserToken
import com.m.one.message.user.LoginRequest
import com.m.one.message.user.UserRequest
import com.m.one.message.user.UserResponse
import com.m.one.message.user.UserUpdateRequest
import io.swagger.v3.oas.annotations.Operation
import mu.KLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$BASE_URL")
class UserController(
    private val userService: UserService,
    private val userApiService: UserApiService,
    private val loginService: LoginService
) {

    companion object : KLogging() {
        const val BASE_URL = "users"
    }

    @Operation(summary = "sign up", description = "회원 가입")
    @PostMapping
    fun reg(@RequestBody userRequest: UserRequest): UserResponse {
        logger.info { "url: /users, param: $userRequest" }
        return userApiService.reg(userRequest.email, userRequest.password)
    }

    @Operation(summary = "user info", description = "회원 정보")
    @GetMapping("/{id}")
    fun getUser(
        @RequestHeader("H-USER-ID") header: String,
        @PathVariable id: Long
    ): UserResponse {
        logger.info { "url: /users/id/$id header: $header" }
        return userService.getUser(id)
    }

    @Operation(summary = "user info update", description = "회원 정보 수정")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody userUpdateRequest: UserUpdateRequest): UserResponse {
        logger.info { "url: /users/id/$id, param: $userUpdateRequest" }
        return userService.update(id, userUpdateRequest)
    }

    @Operation(summary = "sign up", description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): UserToken {
        logger.info { "url: /users/login, param: $loginRequest" }
        return loginService.signUp(loginRequest.email, loginRequest.password)
    }

}