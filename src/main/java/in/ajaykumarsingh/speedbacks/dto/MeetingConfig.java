package in.ajaykumarsingh.speedbacks.dto;

import java.time.LocalTime;

public record MeetingConfig(int id,
                            String member,
                            String meetingWith,
                            int roomNumber,
                            LocalTime time) { }
