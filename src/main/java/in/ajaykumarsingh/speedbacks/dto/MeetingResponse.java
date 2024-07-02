package in.ajaykumarsingh.speedbacks.dto;

import java.util.List;

public record MeetingResponse(String meetingId, List<MeetingConfig> meetingConfigs) {
}
