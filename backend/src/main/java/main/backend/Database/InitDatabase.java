package main.backend.Database;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Pojo.Artist;
import main.backend.Pojo.Event;
import main.backend.Pojo.Rating;
import main.backend.Repositories.ArtistRepository;
import main.backend.Repositories.EventRepository;
import main.backend.Repositories.RatingRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase {

    private final ArtistRepository artistRepository;
    private final EventRepository eventRepository;
    private final RatingRepository ratingRepository;

    @PostConstruct
    public void createDataFromFile() {
        if (artistRepository.count() > 0 || eventRepository.count() > 0) {
            log.info("DB already contains data -> skipping JSON import");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

            List<ArtistJson> artistJsonList = readList(mapper, "/vibe_artist.json", ArtistJson.class);

            Map<String, Artist> artistByFullName = new HashMap<>();
            for (ArtistJson a : artistJsonList) {
                Artist entity = new Artist();
                entity.setFirstname(a.firstName());
                entity.setLastname(a.lastName());
                entity.setDescription(a.description());
                entity.setImageUrl(a.imageUrl());

                Artist saved = artistRepository.save(entity);

                String fullName = fullName(saved.getFirstname(), saved.getLastname());
                artistByFullName.put(fullName, saved);

            }

            log.info("Imported artists: {}", artistByFullName.size());

            List<EventJson> eventJsonList = readList(mapper, "/vibe_event.json", EventJson.class);

            int eventCount = 0;
            int ratingCount = 0;

            for (EventJson e : eventJsonList) {
                Event event = new Event();
                event.setTitle(e.title());
                event.setLocation(e.location());
                event.setEventDate(e.eventDate());
                event.setImageUrl(e.imageUrl());

                if (e.artists() != null) {
                    for (String artistName : e.artists()) {
                        if (artistName == null) continue;
                        Artist artist = artistByFullName.get(artistName.trim());
                        if (artist != null) {
                            event.getArtists().add(artist);
                            artist.getEvents().add(event);
                        } else {
                            log.warn("Event '{}' references unknown artist '{}'", e.title(), artistName);
                        }
                    }
                }

                Event savedEvent = eventRepository.save(event);
                eventCount++;

                if (e.ratings() != null) {
                    for (RatingJson r : e.ratings()) {
                        Rating rating = new Rating();
                        rating.setStars(r.stars());
                        rating.setComment(r.comment());
                        rating.setCreatedAt(r.createdAt() != null ? r.createdAt() : LocalDateTime.now());
                        rating.setEvent(savedEvent);

                        ratingRepository.save(rating);
                        ratingCount++;
                    }
                }
            }

            log.info("Imported events: {}", eventCount);
            log.info("Imported ratings: {}", ratingCount);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String fullName(String firstName, String lastName) {
        String fn = firstName == null ? "" : firstName.trim();
        String ln = lastName == null ? "" : lastName.trim();
        return (fn + " " + ln).trim();
    }

    private <T> List<T> readList(ObjectMapper mapper, String resourcePath, Class<T> clazz) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return mapper.readerForListOf(clazz).readValue(inputStream);
    }

    public record ArtistJson(
            String firstName,
            String lastName,
            String description,
            String imageUrl
    ) {}

    public record EventJson(
            String title,
            String location,
            LocalDate eventDate,
            String imageUrl,
            List<String> artists,
            List<RatingJson> ratings
    ) {}

    public record RatingJson(
            int stars,
            String comment,
            LocalDateTime createdAt
    ) {}
}
