package com.resep.resepapp.controller

import com.resep.resepapp.dto.LoginDTO
import com.resep.resepapp.dto.Message
import com.resep.resepapp.dto.RegisterDTO
import com.resep.resepapp.entity.User
import com.resep.resepapp.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class AuthController(
    private val userService: UserService
) {
    var checkLogin = User()
    fun getChekLogin(check: String): User{
        return userService.getById(check.toInt())
    }
    @PostMapping("/register")
    fun register(
        @RequestBody body: RegisterDTO
    ): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.job = body.job
        user.email = body.email
        user.password = body.password
        user.phone = body.phone
        return ResponseEntity(userService.save(user), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginDTO, response: HttpServletResponse
    ): ResponseEntity<Any> {
        val user = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("User not found"))

        if (!user.comparePassword(body.password)){
            return ResponseEntity.badRequest().body(Message("invalid password"))
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))//1 hari
            .signWith(SignatureAlgorithm.HS256, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity(Message("Succes Login"), HttpStatus.OK)
    }

    @GetMapping("user")
    fun user(
        @CookieValue("jwt") jwt: String?
    ): ResponseEntity<Any>{
       try {
           if (jwt == null){
               return ResponseEntity.status(401).body(Message("Your not logged"))
           }
           val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body

           checkLogin = getChekLogin(body.issuer)

           return ResponseEntity(userService.getById(body.issuer.toInt()), HttpStatus.OK)

       }catch (e: Exception){
           return ResponseEntity.status(401).body(Message("Your not logged"))
       }
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Any>{
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity(Message("Succes Logout"), HttpStatus.OK)
    }
}