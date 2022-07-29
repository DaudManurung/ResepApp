package com.resep.resepapp.service

import com.resep.resepapp.entity.Resep
import com.resep.resepapp.repository.ResepRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResepService(
    private val resepRepository: ResepRepository
) {
    fun getAllResep(): List<Resep>{
        return resepRepository.findAll()
    }

    fun addResep(resep: Resep): Resep{
        return resepRepository.save(resep)
    }


    fun getResepById(id: Int): Resep {
        return resepRepository.getById(id)
    }

    fun updateResep(id: Int, resep: Resep): Resep{
        return  resepRepository.save(resep)
    }
    fun deleteResep(id: Int): String{
        val checkResep = resepRepository.getById(id)
        resepRepository.deleteById(id)
        return "Your Resep has Deleted"
    }

    fun findByTitle(title: String): List<Resep>?{
        return resepRepository.findByTitle(title)
    }

    fun getDetailResep(id: Int): Resep {
        return resepRepository.getById(id)
    }

}