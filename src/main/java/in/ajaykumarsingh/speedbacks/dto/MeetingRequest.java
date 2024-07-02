package in.ajaykumarsingh.speedbacks.dto;

import java.time.LocalTime;
import java.util.List;

public record MeetingRequest(LocalTime startTime, long intervalInMinutes, List<String> members) {}
