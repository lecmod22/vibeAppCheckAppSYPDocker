package main.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import main.backend.Dto.CreateRatingDto;
import main.backend.Dto.RatingDto;
import main.backend.Pojo.Event;
import main.backend.Pojo.Rating;
import main.backend.Repositories.EventRepository;
import main.backend.Repositories.RatingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final EventRepository eventRepository;

    public List<RatingDto> getRatingsForEvent(Long eventId) {
        return ratingRepository.findByEvent_IdOrderByCreatedAtDesc(eventId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public RatingDto addRating(Long eventId, CreateRatingDto req) {
        if (req.getStars() < 1 || req.getStars() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stars must be between 1 and 5");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + eventId));

        Rating rating = new Rating();
        rating.setStars(req.getStars());
        rating.setComment(req.getComment());
        rating.setCreatedAt(LocalDateTime.now());
        rating.setEvent(event);

        Rating saved = ratingRepository.save(rating);
        return toDto(saved);
    }

    private RatingDto toDto(Rating r) {
        RatingDto dto = new RatingDto();
        dto.setStars(r.getStars());
        dto.setComment(r.getComment());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}

