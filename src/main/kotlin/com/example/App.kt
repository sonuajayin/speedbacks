package com.example

import MeetingGenerator
import java.time.LocalTime
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}

fun generateCsv() {
    println("********** Welcome to Speedbacks **********")
    println("Please enter the time to start in [HH:mm]: ")
    val (h, m) = readln().split(':')
    println("Please enter the time interval in minutes: ")
    val intervalInMinutes = readln().toLong()
    val meetingGenerator = MeetingGenerator(LocalTime.of(h.toInt(), m.toInt()), intervalInMinutes)
    meetingGenerator.generate()
}
