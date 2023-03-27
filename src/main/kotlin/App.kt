import java.time.LocalTime

fun main() {
    println("********** Welcome to Speedbacks **********")
    println("Please enter the time to start in [HH:mm]: ")
    val (h, m) = readLine()!!.split(':')
    println("Please enter the time interval in minutes: ")
    val intervalInMinutes=readLine()!!.toLong()
    val meetingGenerator = MeetingGenerator(LocalTime.of(h.toInt(), m.toInt()), intervalInMinutes)
    meetingGenerator.generate()
}