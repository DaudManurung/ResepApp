package com.resep.resepapp.controller

import com.resep.resepapp.dto.Message
import com.resep.resepapp.dto.ResepDTO
import com.resep.resepapp.dto.SearchDTO
import com.resep.resepapp.entity.Resep
import com.resep.resepapp.repository.ResepRepository
import com.resep.resepapp.service.ResepService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/resep"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ResepController (
    private val resepService: ResepService,
    private val resepRepository: ResepRepository,
    private val authController: AuthController
){
    @GetMapping("/allResep")
    fun getAllResep():ResponseEntity<List<Resep>>{
        return ResponseEntity(resepService.getAllResep(), HttpStatus.OK)
    }

    @PostMapping("/addResep")
    fun addResep(
        @RequestBody body: ResepDTO,
        @CookieValue("jwt") jwt : String?
    ): ResponseEntity<Any>{

        val check = authController.checkLogin

        try {

            if (jwt == null){
                return ResponseEntity.status(401).body(Message("Anda belum Login"))
            }
            val jwtID = Jwts.parser().setSigningKey("secreet").parseClaimsJwt(jwt).body
            val resep = Resep()

            body.user_id = check
            resep.title = body.title
            resep.user_id = check
            resep.description = body.description
            resep.ingredients = body.ingredients
            resep.steps = body.steps
            resep.pictures = body.pictures
            resep.created_at = body.created_at
            resep.updated_at = body.updated_at

            return ResponseEntity(resepService.addResep(resep), HttpStatus.OK)

        }catch (e: Exception){
            return ResponseEntity.status(401).body(Message(e.message.toString()))
        }

    }

    @PutMapping("/updateResep/{id}")
    fun updateResep(
        @PathVariable("id") id: Int,
        @RequestBody body: ResepDTO,
        @CookieValue("jwt") jwt: String?
    ): ResponseEntity<Any> {

        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("Anda belum Login"))
            }
            val jwtID = Jwts.parser().setSigningKey("secreet").parseClaimsJwt(jwt).body

            val check = authController.checkLogin
            val resep: Resep = resepService.getResepById(id)

            resep.title = body.title
            resep.description = body.description
            resep.ingredients = body.ingredients
            resep.steps = body.steps
            resep.pictures = body.pictures
            resep.updated_at = body.updated_at

            if (check.id != resep.user_id?.id){
                return ResponseEntity.status(401).body(Message("Ini bukan resep buatan mu!! Kamu tidak bisa mengupdate nya"))
            }

            return ResponseEntity(resepService.updateResep(id,resep), HttpStatus.OK)

        } catch (e: Exception){
            return ResponseEntity.status(401).body(Message("Anda belum Login"))
         }


    }

    @DeleteMapping("/deleteResep/{id}")
    fun deleteResep(
        @PathVariable("id") id: Int
    ): ResponseEntity<Any>{
        val check = authController.checkLogin
        val resep: Resep = resepService.getResepById(id)

        if (check.id != resep.user_id?.id){
            return ResponseEntity.status(401).body(Message("Ini bukan resep buatan mu!! Kamu tidak bisa menghapusnya"))
        }

        return ResponseEntity(resepService.deleteResep(id), HttpStatus.OK)
    }

//    @PostMapping("/search")
//    fun search(
//        @RequestBody() searchDTO: SearchDTO
//    ):ResponseEntity<List<Resep>>{
//        return ResponseEntity(this.resepRepository.findByTitle(searchDTO.key), HttpStatus.OK)
//    }

    @GetMapping("/search")
    fun search(
        @RequestParam("title", defaultValue = "") title: String
    ):ResponseEntity<List<Resep>>{
        return ResponseEntity(resepRepository.findByTitle(title), HttpStatus.OK)
    }


    @GetMapping("/detail/{id}")
    fun detail(
        @PathVariable("id") id: Int,
        @CookieValue("jwt") jwt: String?
    ):ResponseEntity<Resep>{
        return ResponseEntity(resepService.getDetailResep(id), HttpStatus.OK)
    }


}