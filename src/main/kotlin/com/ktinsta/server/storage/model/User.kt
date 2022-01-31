package com.ktinsta.server.storage.model

import com.ktinsta.server.listeners.UserListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "`user`")
@EntityListeners(UserListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @get: NotBlank
    var email: String = "",

    @Column(unique = true)
    @get: NotBlank
    var username: String = "",

    @get: NotBlank
    var password: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    var avatar: Image? = null,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)