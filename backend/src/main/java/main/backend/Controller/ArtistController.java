package main.backend.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.backend.Dto.ArtistDto;
import main.backend.Pojo.Artist;
import main.backend.Services.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping
    public List<ArtistDto> getAllArtists() {
        return artistService.getAllArtists();
    }
}
