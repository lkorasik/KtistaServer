package com.ktinsta.server.service

import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.resizers.BicubicResizer
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class AvatarService {
    fun makeThumbnail(bytes: ByteArray): ByteArray {
        val source = bytes.inputStream()
        val result = ByteArrayOutputStream()

        Thumbnails
            .of(source)
            .resizer(BicubicResizer())
            .size(512, 512)
            .toOutputStream(result)

        return result.toByteArray()
    }
}