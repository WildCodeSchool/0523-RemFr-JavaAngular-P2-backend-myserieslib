package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String pictureUrl;

    @ManyToMany(mappedBy = "actors")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Serie> series = new ArrayList<>();

    public Actor(String firstname, String lastname, String pictureUrl) {
        this.pictureUrl = pictureUrl;
        this.firstName = firstname;
        this.lastName = lastname;
    }
}
