package main.backend.Controller;

import lombok.RequiredArgsConstructor;
import main.backend.Dto.CreateRatingDto;
import main.backend.Dto.RatingDto;
import main.backend.Services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{eventId}")
    public ResponseEntity<RatingDto> addRating(@PathVariable Long eventId, @RequestBody CreateRatingDto createRatingDto) {
        RatingDto created = ratingService.addRating(eventId, createRatingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
