package main.backend.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EventDto {
    private String title;
    private String location;
    private LocalDate eventDate;
    private String imageUrl;
    private double avgRating;
    private long ratingCount;
}
