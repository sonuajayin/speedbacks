package com.example.models

import java.time.LocalTime

data class MeetingConfig(
    val id: Int,
    val member: String,
    val meetingWith: String,
    val roomNumber: Int,
    val time: LocalTime
)
