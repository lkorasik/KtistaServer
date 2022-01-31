package com.ktinsta.server.storage.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToMany(targetEntity = User::class)
    @JoinColumn
    @get: NotBlank
    var user: List<User>,

    @ManyToOne(targetEntity = User::class)
    @JoinColumn
    @get: NotBlank
    var follower: List<User>
)