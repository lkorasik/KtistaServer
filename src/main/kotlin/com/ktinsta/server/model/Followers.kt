package com.ktinsta.server.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToMany(targetEntity = User::class)
    @JoinColumn(referencedColumnName = "id")
    @get: NotBlank
    var user: List<User?>? = null,

    @get: NotBlank
    var followerId: Long = 0
)