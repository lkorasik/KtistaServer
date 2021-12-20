package com.ktinsta.server.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @OneToOne(targetEntity = Post::class)
    @JoinColumn(referencedColumnName = "id")
    var post: Post? = null,

    var data: ByteArray? = null,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)