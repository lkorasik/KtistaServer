package com.ktinsta.server.helpers.objects

data class PostVO(
    var userId: Long,
    var text: String,
    var data: ByteArray
)