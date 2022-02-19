package com.m.one.domain.service.user

import com.m.one.domain.exception.AlreadyUserException
import com.m.one.domain.exception.NoUserException
import com.m.one.domain.model.type.Role
import com.m.one.domain.model.user.User
import com.m.one.domain.repository.user.UserRepository
import com.m.one.message.user.UserResponse
import com.m.one.message.user.UserUpdateRequest
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.print.Pageable

@Service
@Transactional(readOnly = true)
class UserService(
    @Value("\${token.expired.base}")
    private val baseExpired: Long,
    @Value("\${token.expired.refresh}")
    private val refreshExpired: Long,
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

    fun getUser(id: Long, email: String): UserResponse {
        return userRepository.findByIdAndEmail(id, email)?.run {
            this.toUserResponse()
        } ?: run {
            throw NoUserException(NO_USER_MSG)
        }
    }

    fun getUser(email: String, password: String): User {
        return userRepository.findByEmail(email)?.let {
            if (!passwordEncoder.matches(password, it.password)) {
                throw NoUserException(NO_USER_MSG)
            } else {
                it
            }
        } ?: run {
            throw NoUserException(NO_USER_MSG)
        }
    }

    fun getUserList(pageable: Pageable) {
        //일단 pass
    }

}