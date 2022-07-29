package com.resep.resepapp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "user")
 class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "job")
    var job: String? = null

    @Column(name = "email", unique = true)
    var email: String? = null

    @Column(name = "password")
    var password: String? = null
        @JsonIgnore
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    fun comparePassword(password: String): Boolean{
        return BCryptPasswordEncoder().matches(password, this.password)
    }

    @Column(name = "phone")
    var phone: String? = null
}

