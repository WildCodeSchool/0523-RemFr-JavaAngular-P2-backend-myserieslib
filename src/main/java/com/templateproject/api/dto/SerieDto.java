package com.templateproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieDto {
    private UUID id;
    private String name;
    private String producer;
    private String pictureUrlXL;
    private String pictureUrlXS;
    private String trailerURL;
    private LocalDate releaseDate;
    private String description;
    private Boolean isCompleted;
    private List<CategoryDto> categories;
}
