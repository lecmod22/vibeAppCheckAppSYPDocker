package main.backend.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRatingDto {

    private int stars;
    private String comment;
}
