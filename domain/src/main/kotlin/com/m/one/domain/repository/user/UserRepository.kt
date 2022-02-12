package com.m.one.domain.repository.user

import com.m.one.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun findByIdAndEmail(id: Long, email: String): User?

}