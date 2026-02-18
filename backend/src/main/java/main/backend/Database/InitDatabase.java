package main.backend.Database;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Pojo.Artist;
import main.backend.Pojo.Event;
import main.backend.Repositories.ArtistRepository;
import main.backend.Repositories.EventRepository;
import main.backend.Repositories.RatingRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
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
            log.info("Database already contains data");
            return;
        } else {
            importArtistsAndEvents();
        }
    }

    private void importArtistsAndEvents() {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

            List<ArtistJson> artistJson = readListIfExists(mapper, "/vibe_artist.json", ArtistJson.class);

            Map<Long, Artist> artistByJsonId = new HashMap<>();

            for (ArtistJson a : artistJson) {
                Artist entity = new Artist();
                entity.setFirstname(a.firstname());
                entity.setLastname(a.lastname());
                entity.setDescription(a.description());
                entity.setImageUrl(a.imageUrl());

                Artist saved = artistRepository.save(entity);
                artistByJsonId.put(a.id(), saved);
            }

            List<EventJson> eventJson = readListIfExists(mapper, "/vibe_event.json", EventJson.class);
            if (eventJson == null) throw new IOException("Missing /vibe_event.json");

            Map<Long, Event> eventByJsonId = new HashMap<>();

            int eventCount = 0;
            for (EventJson e : eventJson) {
                Event entity = new Event();
                entity.setTitle(e.title());
                entity.setLocation(e.location());
                entity.setEventDate(e.eventDate());
                entity.setImageUrl(e.imageUrl());

                if (e.artistIds() != null) {
                    for (Long artistId : e.artistIds()) {
                        Artist artist = artistByJsonId.get(artistId);
                        if (artist != null) {
                            entity.getArtists().add(artist);
                            artist.getEvents().add(entity);
                        }
                    }
                }

                Event saved = eventRepository.save(entity);
                if (e.id() != null) eventByJsonId.put(e.id(), saved);
                eventCount++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importRatingsIfFileExists() {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            List<RatingJson> ratingJson = readListIfExists(mapper, "/vibe_rating.json", RatingJson.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> List<T> readListIfExists(ObjectMapper mapper, String resourcePath, Class<T> clazz) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) return null;
        return mapper.readerForListOf(clazz).readValue(inputStream);
    }

    public record ArtistJson(
            Long id,
            String firstname,
            String lastname,
            String description,
            String imageUrl
    ) {}

    public record EventJson(
            Long id,
            String title,
            String location,
            java.time.LocalDate eventDate,
            String imageUrl,
            List<Long> artistIds
    ) {}

    public record RatingJson(
            Long id,
            Long eventId,
            int stars,
            String comment,
            LocalDateTime createdAt
    ) {}
}
