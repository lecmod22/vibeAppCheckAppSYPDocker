package main.backend.Pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(length = 4000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}

