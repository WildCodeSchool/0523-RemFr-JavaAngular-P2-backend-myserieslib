package com.templateproject.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private Integer episodeNumber;
    private String title;
    private Integer seasonNumber;
    private String thumbnail;
    private String description;
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToMany(mappedBy = "episodes")
    private List<User> users;
}
