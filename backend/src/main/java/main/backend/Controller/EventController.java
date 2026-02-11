package main.backend.Controller;

import lombok.RequiredArgsConstructor;
import main.backend.Dto.EventDto;
import main.backend.Services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDto>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }
}
