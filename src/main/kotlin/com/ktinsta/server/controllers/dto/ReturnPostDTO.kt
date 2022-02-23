package com.ktinsta.server.controllers.dto

data class ReturnPostDTO(
    var author: ShortUserDTO,
    var text: String,
    var image: ByteArray
)
