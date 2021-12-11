package com.ktinsta.server.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

/*
@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(targetEntity = Post::class)
    @JoinColumn(referencedColumnName = "id")
    @get: NotBlank
    var post: Post? = null,

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(referencedColumnName = "id")
    var user: User? = null,

    @get: NotBlank
    var text: String = "",

    @get: NotBlank
    var likesCounter: Int = 0,

    @get: NotBlank
    var dislikesCounter: Int = 0,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)*/