package com.ktinsta.server.storage.model

import javax.persistence.*


@Entity
data class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn
    var fullUser: FullUser,

    @ManyToOne
    @JoinColumn
    var follower: FullUser
)
