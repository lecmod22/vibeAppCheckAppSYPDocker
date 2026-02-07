package main.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.backend.Dto.EventDto;
import main.backend.Pojo.Event;
import main.backend.Repositories.EventRepository;
import main.backend.Repositories.RatingRepository;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final RatingRepository ratingRepository;

    public Page<EventDto> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(this::toDtoWithStats);
    }

    public EventDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + eventId));
        return toDtoWithStats(event);
    }

    public Page<EventDto> getEventsByArtist(Long artistId, Pageable pageable) {
        return eventRepository.findDistinctByArtists_Id(artistId, pageable).map(this::toDtoWithStats);
    }

    private EventDto toDtoWithStats(Event e) {
        EventDto dto = new EventDto();
        dto.setTitle(e.getTitle());
        dto.setLocation(e.getLocation());
        dto.setEventDate(e.getEventDate());
        dto.setImageUrl(e.getImageUrl());

        double avg = ratingRepository.avgStarsByEventId(e.getId());
        long count = ratingRepository.countByEventId(e.getId());

        dto.setAvgRating(avg);
        dto.setRatingCount(count);

        return dto;
    }
}
