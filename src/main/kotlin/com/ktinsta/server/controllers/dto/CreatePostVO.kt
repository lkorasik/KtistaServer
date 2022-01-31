package com.ktinsta.server.controllers.dto

data class CreatePostVO(
    var text: String,
    var data: ByteArray
)