package com.ktinsta.server.storage.model

import javax.persistence.*

@Entity
data class PostLike(
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
