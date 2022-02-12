package com.m.one.api.controller

import com.m.one.api.controller.UserController.Companion.BASE_URL
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
    private val userService: UserService
) {

    companion object : KLogging() {
        const val BASE_URL = "users"
    }

    @Operation(summary = "sign up", description = "회원 가입")
    @PostMapping
    fun reg(@RequestBody userRequest: UserRequest): UserResponse {
        logger.info { "url: /users, param: ${userRequest.toString()}" }
        return userService.insert(userRequest.email, userRequest.password)
    }

    @Operation(summary = "user info", description = "회원 정보")
    @GetMapping("/id/{id}")
    fun getUser(@PathVariable id: Long, @RequestParam email: String): UserResponse {
        logger.info { "url: /users/id/$id, param: $email" }
        return userService.getUser(id, email)
    }

    @Operation(summary = "user info update", description = "회원 정보 수정")
    @PutMapping("/id/{id}")
    fun update(@PathVariable id: Long, @RequestBody userUpdateRequest: UserUpdateRequest): UserResponse {
        logger.info { "url: /users/id/$id, param: $userUpdateRequest" }
        return userService.update(id, userUpdateRequest)
    }

    @Operation(summary = "sign in", description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): UserToken {
        logger.info { "url: /users/login, param: $loginRequest" }
        return userService.login(loginRequest.email, loginRequest.password)
    }

}