package com.templateproject.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private Set<Serie> serie;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LibraryStatus status;

    public Library(Integer score, String comment) {
        this.comment = comment;
        this.score = score;
    }
}
