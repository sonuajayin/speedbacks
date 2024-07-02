package in.ajaykumarsingh.speedbacks;

import in.ajaykumarsingh.speedbacks.dto.MeetingConfig;
import in.ajaykumarsingh.speedbacks.dto.MeetingRequest;
import in.ajaykumarsingh.speedbacks.dto.MeetingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class SpeedBacksController {

    private final MeetingGeneratorService meetingGeneratorService;


    public SpeedBacksController(MeetingGeneratorService meetingGeneratorService) {
        this.meetingGeneratorService = meetingGeneratorService;
    }

    @PostMapping("/generate")
    public MeetingResponse generateMeetings(@RequestBody MeetingRequest request) {
        return meetingGeneratorService.generateMeetings(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MeetingConfig>> getMeetingResponse(@PathVariable String id) {
        List<MeetingConfig> response = meetingGeneratorService.getMeetingConfigs(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
