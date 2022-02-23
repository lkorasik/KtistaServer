package com.ktinsta.server.controllers

import com.ktinsta.server.service.ImageService
import io.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayInputStream


@RestController
@RequestMapping("/api/image")
@Api(tags = ["Image"], description = "This controller handles images")
class ImageController(val imageService: ImageService) {

    @GetMapping("/get/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val image = imageService.get(id)
        val bytes = ByteArrayInputStream(image.data).readAllBytes()

        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes);
    }
}
