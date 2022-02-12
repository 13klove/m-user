package com.m.one.domain.service.token

import com.m.one.domain.model.type.TokenType
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService{

    fun getToken(token: TokenType, salt: String, expired: Long, detail: Map<String, Any>): String {
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("tokenType", token)
            .setClaims(detail)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expired))
            .signWith(SignatureAlgorithm.HS256, salt)
            .compact()
    }

}