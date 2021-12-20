package com.ktinsta.server.model

import javax.persistence.*

@Entity
data class PostDislike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(targetEntity = Post::class)
    @JoinColumn(referencedColumnName = "id")
    var post: Post? = null,

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(referencedColumnName = "id")
    var user: User? = null
)