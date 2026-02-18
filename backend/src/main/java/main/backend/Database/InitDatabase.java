package main.backend.Database;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Repositories.ArtistRepository;
import main.backend.Repositories.EventRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase {

    private final ArtistRepository artistRepository;
    private final EventRepository eventRepository;

    @PostConstruct
    public void createDataFromFile() {
        if (artistRepository.count() > 0 || eventRepository.count() > 0) {
            log.info("Database already contains data");
            return;
        }

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    private <T> List<T> readList(ObjectMapper mapper, String resourcePath, Class<T> clazz) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return null;
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
}
