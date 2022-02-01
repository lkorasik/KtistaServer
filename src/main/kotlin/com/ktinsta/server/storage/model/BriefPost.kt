package com.ktinsta.server.storage.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "post")
data class BriefPost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn
    var author: BriefUser,

    @OneToOne
    @JoinColumn
    var image: Image,

    var text: String = "",

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)