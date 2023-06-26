package com.example.speedbacks

data class SpeedBackRequestDto(
    val startTime: String,
    val intervalInMinutes: Int,
    val teamMembers: Map<String, String>
)