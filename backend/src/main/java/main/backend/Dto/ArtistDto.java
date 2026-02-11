package main.backend.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistDto {
    private int id;
    private String firstname;
    private String lastname;
    private String description;
    private String imageUrl;
}
