package com.ktinsta.server.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    //TODO: Прописать каскадное удаление
    @ManyToOne(targetEntity = User::class, cascade= [CascadeType.REMOVE])
    @JoinColumn(referencedColumnName = "id")
    var author: User? = null,

    var text: String = "",

    var likesCounter: Int = 0,

    var dislikesCounter: Int = 0,

    var commentCounter: Int = 0,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)