package com.m.one.domain.service.user

import com.m.one.domain.exception.AlreadyUserException
import com.m.one.domain.exception.NoUserException
import com.m.one.domain.model.type.Role
import com.m.one.domain.model.type.TokenType
import com.m.one.domain.model.user.User
import com.m.one.domain.repository.user.UserRepository
import com.m.one.domain.service.token.TokenService
import com.m.one.message.token.UserToken
import com.m.one.message.user.UserResponse
import com.m.one.message.user.UserUpdateRequest
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.print.Pageable
import java.security.Key
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    @Value("\${token.expired.base}")
    private val baseExpired: Long,
    @Value("\${token.expired.refresh}")
    private val refreshExpired: Long,
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    companion object : KLogging() {
        const val ALREADY_USER_MSG = "already user"
        const val NO_USER_MSG = "no user"
    }

    @Transactional
    fun insert(email: String, password: String): UserResponse {
        userRepository.findByEmail(email)?.run {
            throw AlreadyUserException(ALREADY_USER_MSG)
        }

        return userRepository.save(
            User.create(
                email,
                passwordEncoder.encode(password),
                Role.USER,
                UUID.randomUUID().toString()
            )
        ).toUserResponse()
    }

    @Transactional
    fun update(id: Long, userUpdateRequest: UserUpdateRequest): UserResponse {
        return with(userUpdateRequest) {
            userRepository.findByIdAndEmail(id, email)?.let {
                if (passwordEncoder.matches(password, it.password)) {
                    it.password = passwordEncoder.encode(newPassword)
                    it.toUserResponse()
                } else {
                    throw NoUserException(NO_USER_MSG)
                }
            } ?: run {
                throw NoUserException(NO_USER_MSG)
            }
        }
    }

    fun login(email: String, password: String): UserToken {
        userRepository.findByEmail(email)?.let {
            if (passwordEncoder.matches(password, it.password)) {
                val detail = mutableMapOf<String, Any>("email" to it.email)
                return UserToken(
                    tokenService.getToken(TokenType.BASE, it.salt, baseExpired, detail),
                    tokenService.getToken(TokenType.REFRESH, it.salt, refreshExpired, detail)
                )
            } else {
                throw NoUserException(NO_USER_MSG)
            }
        } ?: run {
            throw NoUserException(NO_USER_MSG)
        }
    }

    fun getUser(id: Long, email: String): UserResponse {
        return userRepository.findByIdAndEmail(id, email)?.run {
            this.toUserResponse()
        } ?: run {
            throw NoUserException(NO_USER_MSG)
        }
    }

    fun getUserList(pageable: Pageable) {
        //일단 pass
    }

}