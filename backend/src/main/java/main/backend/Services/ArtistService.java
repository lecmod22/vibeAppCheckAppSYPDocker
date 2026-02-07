package main.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.backend.Dto.ArtistDto;
import main.backend.Pojo.Artist;
import main.backend.Repositories.ArtistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public List<ArtistDto> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ArtistDto getArtistById(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found: " + artistId));
        return toDto(artist);
    }

    private ArtistDto toDto(Artist a) {
        ArtistDto dto = new ArtistDto();
        dto.setFirstname(a.getFirstname());
        dto.setLastname(a.getLastname());
        dto.setDescription(a.getDescription());
        dto.setImageUrl(a.getImageUrl());
        return dto;
    }
}
