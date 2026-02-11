package main.backend.Controller;

import lombok.RequiredArgsConstructor;
import main.backend.Dto.RatingDto;
import main.backend.Services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<RatingDto>> getRatingsForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(ratingService.getRatingsForEvent(eventId));
    }
}
