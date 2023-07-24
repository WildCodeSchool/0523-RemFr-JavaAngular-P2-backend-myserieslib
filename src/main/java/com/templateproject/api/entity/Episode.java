package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer episodeNumber;
    private String title;
    private Integer seasonNumber;
    private String thumbnail;
    @Column(columnDefinition = "text")
    private String description;
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToMany(mappedBy = "episodes")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<User> users;

    public Episode(Integer episodeNumber, String title, Integer seasonNumber, String thumbnail, String description, LocalDate releaseDate) {
        this.description = description;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.seasonNumber = seasonNumber;
        this.releaseDate = releaseDate;
        this.thumbnail = thumbnail;
    }
}
