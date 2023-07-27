package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "categories")

    @OnDelete(action = OnDeleteAction.CASCADE)

    @JsonIgnore
    private List<Serie> series = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
}
