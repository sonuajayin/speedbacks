package com.example.speedbacks

import java.net.URI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/speedbacks")
class SpeedBacksController(private val speedBackGeneratorService: SpeedBackGeneratorService) {

    @PostMapping
    fun createUser(@RequestBody request: SpeedBackRequestDto): ResponseEntity<SpeedBacksResponse> {
        val createdSpeedBacks = speedBackGeneratorService.generate(request)
        return ResponseEntity.created(URI.create("/api/speedbacks/someid")).body(createdSpeedBacks)
    }
}