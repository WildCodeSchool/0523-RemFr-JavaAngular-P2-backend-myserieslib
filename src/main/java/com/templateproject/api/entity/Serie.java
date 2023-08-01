package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String producer;
    @Column(columnDefinition = "text")
    private String pictureUrlXL;
    @Column(columnDefinition = "text")
    private String pictureUrlXS;
    private String trailerURL;
    private LocalDate releaseDate;
    @Column(columnDefinition = "text")
    private String description;
    private Boolean isCompleted;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "serie_category",
            joinColumns = @JoinColumn(name = "serie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "serie")
    @JsonIgnore
    private List<Episode> episodes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "serie_actor",
            joinColumns = @JoinColumn(name = "serie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors = new ArrayList<>();

    public Serie(String name, String producer, String pictureUrlXL, String pictureUrlXS, String trailerURL, LocalDate releaseDate, String description, Boolean isCompleted) {
        this.name = name;
        this.producer = producer;
        this.pictureUrlXL = pictureUrlXL;
        this.pictureUrlXS = pictureUrlXS;
        this.trailerURL = trailerURL;
        this.releaseDate = releaseDate;
        this.isCompleted= isCompleted;
        this.description = description;
    }
}