package com.m.one.domain.service.user

import com.m.one.domain.model.type.TokenType
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

//https://escapefromcoding.tistory.com/255
class UserServiceTest {

    companion object {

    }

    @Test
    fun `jwt 암호화 복호화 테스트`(){
        val key = UUID.randomUUID().toString()
        val now = Date()
        val claimsMap = mutableMapOf<String, Any>("tokenType" to "base", "email" to "abc@xxx.com")
        val jwt = Jwts.builder()
            .setHeaderParam("tokenType", TokenType.BASE)
            .setClaims(claimsMap)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + 86500000))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        Assertions.assertTrue(jwt.isNotBlank())

        val claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(jwt)
            .body
        println(claims)
        println(claims["tokenType"])
        println(claims["email"])

        val header = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(jwt)
            .header
        println(header["tokenType"])
    }

}