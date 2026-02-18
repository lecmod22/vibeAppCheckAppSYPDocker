package main.backend.Database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Repositories.ArtistRepository;
import main.backend.Repositories.EventRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase {

    private final ArtistRepository artistRepository;
    private final EventRepository eventRepository;

}
