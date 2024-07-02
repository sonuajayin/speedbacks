package in.ajaykumarsingh.speedbacks;

import in.ajaykumarsingh.speedbacks.dto.MeetingConfig;
import in.ajaykumarsingh.speedbacks.dto.MeetingRequest;
import in.ajaykumarsingh.speedbacks.dto.MeetingResponse;
import in.ajaykumarsingh.speedbacks.utils.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MeetingGeneratorService {
    private final List<MeetingConfig> meetingConfigs = new ArrayList<>();
    private final String lounge = "Lounge";
    private final Map<String, List<MeetingConfig>> meetingConfigsCache = new ConcurrentHashMap<>();

    public MeetingResponse generateMeetings(MeetingRequest request) {
        LocalTime startTime = LocalTime.parse(request.startTime().toString());
        long intervalInMinutes = request.intervalInMinutes();
        List<String> members = request.members();

        scheduler(startTime, intervalInMinutes, members);

        String meetingId = UUIDGenerator.generateUUID();
        meetingConfigsCache.put(meetingId, meetingConfigs);
        return new MeetingResponse(meetingId, meetingConfigs);
    }

    public List<MeetingConfig> getMeetingConfigs(String meetingId) {
        return meetingConfigsCache.get(meetingId);
    }

    private void scheduler(LocalTime startTime, long intervalInMinutes, List<String> members) {
        int meetingId = 1;
        LocalTime time = startTime;
        int numOfMembers = members.size();
        String[] evenMembers;
        int k = 0;

        if (numOfMembers % 2 == 0) {
            evenMembers = new String[numOfMembers - 1];
            while (k < numOfMembers - 1) {
                evenMembers[k] = members.get(k + 1);
                k++;
            }
        } else {
            evenMembers = new String[numOfMembers];
            while (k < numOfMembers - 1) {
                evenMembers[k] = members.get(k + 1);
                k++;
            }
            evenMembers[numOfMembers - 1] = lounge;
        }

        int teamsSize = evenMembers.length;
        int halfSize = (teamsSize + 1) / 2;

        for (int slot = teamsSize - 1; slot >= 0; slot--) {
            int roomNo = 1;
            int teamIndex = slot % teamsSize;

            meetingConfigs.add(new MeetingConfig(meetingId++, members.get(0), evenMembers[teamIndex], roomNo, time));
            roomNo++;

            for (int i = 1; i < halfSize; i++) {
                int firstTeam = (slot + i) % teamsSize;
                int secondTeam = (slot + teamsSize - i) % teamsSize;

                meetingConfigs.add(new MeetingConfig(meetingId++, evenMembers[firstTeam], evenMembers[secondTeam], roomNo, time));
                roomNo++;
            }

            time = time.plusMinutes(intervalInMinutes);
        }
    }
}
