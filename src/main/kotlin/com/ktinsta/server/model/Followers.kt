package com.ktinsta.server.model

import javax.persistence.*


@Entity
data class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn
    var user: User,

    @ManyToOne
    @JoinColumn
    var follower: User
)
