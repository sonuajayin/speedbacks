import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalTime

class MeetingGenerator(private val startTime: LocalTime, val intervalInMinutes: Long) {

    private var meetingConfigs = mutableListOf<MeetingConfig>()
    private val lounge = "Lounge"
    private val teamFile = "TeamMembers"
    private val members = File(teamFile).readLines()

    fun generate() {
        scheduler()
        FileOutputStream("speedbacks.csv").apply { writeCsv(meetingConfigs) }
        println("File Generated")
    }

    @Throws(IOException::class)
    private fun scheduler() {
        var meetingId = 1
        var time = startTime
        val numOfMembers = members.size
        val evenMembers: Array<String?>
        var k=0
        if (numOfMembers % 2 == 0) {
            evenMembers = arrayOfNulls(numOfMembers - 1)
            while (k < numOfMembers - 1) {
                evenMembers[k] = members[k + 1]
                k++
            }
        } else {
            evenMembers = arrayOfNulls(numOfMembers)
            while (k < numOfMembers - 1) {
                evenMembers[k] = members[k + 1]
                k++
            }
            evenMembers[numOfMembers - 1] = lounge
        }
        val teamsSize = evenMembers.size
        val halfSize = (teamsSize + 1) / 2
        for (slot in teamsSize - 1 downTo 0) {
            var roomNo = 1
            println("Time $time")
            val teamIndex = slot % teamsSize
            println("meeting $meetingId " + members[0] + " with " + evenMembers[teamIndex] + " in Room " + roomNo)
            meetingConfigs.add(MeetingConfig(meetingId, members[0], evenMembers[teamIndex]!!, roomNo, time))
            roomNo++
            meetingId++
            for (i in 1 until halfSize) {
                val firstTeam = (slot + i) % teamsSize
                val secondTeam = (slot + teamsSize - i) % teamsSize
                println("meeting $meetingId " + evenMembers[firstTeam] + " with " + evenMembers[secondTeam] + " in Room " + roomNo)
                meetingConfigs.add(
                    MeetingConfig(
                        meetingId,
                        evenMembers[firstTeam]!!,
                        evenMembers[secondTeam]!!,
                        roomNo,
                        time
                    )
                )
                roomNo++
                meetingId++
            }
            println()
            time = time.plusMinutes(intervalInMinutes)
        }
    }

    private fun OutputStream.writeCsv(meetingConfigs: List<MeetingConfig>) {
        val writer = bufferedWriter()
        writer.write("""Member, Time, Room, Meeting With""")
        writer.newLine()
        var lastMember = ""
        val emptySpace = "  "

        for (i in members.indices) {
            val member = members[i]
            if (member != lounge) {
                val filteredPair =
                    meetingConfigs.filter { it.member == member || it.meetingWith == member }.sortedBy { it.id }
                for (pair in filteredPair) {
                    val otherPerson = if (pair.meetingWith != member) pair.meetingWith else pair.member
                    var room = lounge
                    var meetingWith = emptySpace
                    if (otherPerson != lounge) {
                        room = "Room " +pair.roomNumber.toString()
                        meetingWith = otherPerson
                    }
                    val time = pair.time.toString() + " / " + pair.time.minusMinutes(210).toString()
                    if (lastMember != member) {
                        writer.write("${member}, ${time}, $room, $meetingWith")
                        lastMember = member
                    } else {
                        writer.write("${emptySpace}, ${time}, $room, $meetingWith")
                    }
                    writer.newLine()
                }
                writer.newLine()
            }
        }

        writer.flush()
    }
}