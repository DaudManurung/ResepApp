package com.resep.resepapp.repository

import com.resep.resepapp.entity.Resep
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import javax.websocket.server.PathParam

interface ResepRepository: JpaRepository<Resep, Int> {
    @Query("Select * from resep where title like %:title% ", nativeQuery = true)
    fun findByTitle(@PathParam("title") title: String): List<Resep>?

}