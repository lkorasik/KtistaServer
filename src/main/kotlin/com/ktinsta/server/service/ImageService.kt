package com.ktinsta.server.service

import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.storage.repository.ImageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ImageService(val imageRepository: ImageRepository){
    fun create(image: Image) {
        imageRepository.save(image)
    }

    fun get(id: Long): Image {
        return imageRepository.findById(id).get()
    }
}
