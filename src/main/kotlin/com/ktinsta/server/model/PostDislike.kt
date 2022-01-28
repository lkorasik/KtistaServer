package com.ktinsta.server.model

import javax.persistence.*

@Entity
data class PostDislike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn
    var post: Post,

    @ManyToOne
    @JoinColumn
    var user: User
)