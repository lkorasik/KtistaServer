package com.ktinsta.server.model

import javax.persistence.Entity
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val email: String,

    @get: NotBlank
    val username: String,

    @get: NotBlank
    val password: String

)
