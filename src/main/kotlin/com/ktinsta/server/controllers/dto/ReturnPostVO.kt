package com.ktinsta.server.helpers.objects

data class ReturnPostVO(
    var author: ShortUserVO,
    var text: String,
    var data: ByteArray
)