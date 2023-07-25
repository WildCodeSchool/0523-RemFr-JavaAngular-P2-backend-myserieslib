package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer score;
    private String comment;

    @ElementCollection
    private List<Integer> checkedEpisodes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LibraryStatus status;

    public Library(Integer score, String comment) {
        this.comment = comment;
        this.score = score;
    }

    public Library(User user, Serie serie) {
        this.user = user;
        this.serie = serie;
        this.score = null;
        this.comment = null;
        this.status = LibraryStatus.NOT_STARTED;
    }
}
