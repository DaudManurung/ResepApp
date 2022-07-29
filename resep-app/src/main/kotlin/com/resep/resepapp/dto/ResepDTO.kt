package com.resep.resepapp.dto

import com.resep.resepapp.entity.User
import java.sql.Timestamp

class ResepDTO {
    val title = ""
    var user_id : User? = User()
    val description = ""
    val ingredients = ""
    val steps = ""
    val pictures = ""
    val created_at: Timestamp?=null
    val updated_at: Timestamp?=null

}