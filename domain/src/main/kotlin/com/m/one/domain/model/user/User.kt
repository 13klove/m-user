package com.m.one.domain.model.user

import com.m.one.domain.model.type.Role
import com.m.one.message.user.UserResponse
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    var password: String,
    @Enumerated(value = EnumType.STRING)
    var role: Role,
    var createdAt: Long,
    var updatedAt: Long,
    var deletedAt: Long? = null
) {

    companion object {
        fun create(email: String, password: String, role: Role): User {
            val nowTimestamp = Instant.now().toEpochMilli()
            return User(
                email = email,
                password = password,
                role = role,
                createdAt = nowTimestamp,
                updatedAt = nowTimestamp
            )
        }
    }

    fun toUserResponse(): UserResponse = UserResponse(this.id, this.email)

}