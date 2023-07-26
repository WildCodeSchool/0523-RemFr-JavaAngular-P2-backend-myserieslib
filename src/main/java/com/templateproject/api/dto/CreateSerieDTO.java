package com.templateproject.api.dto;

import com.github.javafaker.Bool;
import com.templateproject.api.entity.Actor;
import com.templateproject.api.entity.Category;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.List;

public class CreateSerieDTO {
    private String name;
    private String producer;
    private String pictureUrlXL;
    private String pictureUrlXS;
    private String trailerURL;
    private LocalDate releaseDate;
    private String description;
    private Boolean isCompleted;
    private List<Actor> actors;
    private List<Category> category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPictureUrlXL() {
        return pictureUrlXL;
    }

    public void setPictureUrlXL(String pictureUrlXL) {
        this.pictureUrlXL = pictureUrlXL;
    }

    public String getPictureUrlXS() {
        return pictureUrlXS;
    }

    public void setPictureUrlXS(String pictureUrlXS) {
        this.pictureUrlXS = pictureUrlXS;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
