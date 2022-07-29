package com.resep.resepapp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "resep")
 class Resep {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    //@ManyToOne( cascade = arrayOf(CascadeType.ALL))
    @ManyToOne
    @JoinColumn(name ="user_id")
    var user_id: User? = null

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "ingredients")
    var ingredients: String? = null

    @Column(name = "steps")
    var steps: String? = null

    @Column(name = "pictures")
    var pictures: String? = null

    @Column(name = "created_at")
    var created_at: Timestamp ? = null

    @Column(name = "updated_at")
    var updated_at: Timestamp ? = null


}
