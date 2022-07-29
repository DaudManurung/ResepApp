package com.resep.resepapp.service

import com.resep.resepapp.entity.User
import com.resep.resepapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun save(user: User): User{
        return userRepository.save(user)
    }

    fun findByEmail(email:String): User?{
        return userRepository.findByEmail(email)
    }

    fun getById(id: Int): User{
        return this.userRepository.getById(id)
    }
}