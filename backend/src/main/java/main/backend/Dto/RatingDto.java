package main.backend.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class RatingDto {
    private int stars;
    private String comment;
    private LocalDateTime createdAt;
}
