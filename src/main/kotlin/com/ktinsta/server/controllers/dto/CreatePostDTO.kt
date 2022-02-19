package com.ktinsta.server.controllers.dto

data class CreatePostDTO(
    var text: String,
    var image: ByteArray
)