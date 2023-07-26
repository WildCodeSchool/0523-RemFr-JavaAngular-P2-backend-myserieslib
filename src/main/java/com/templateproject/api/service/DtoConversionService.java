package com.templateproject.api.service;

import com.templateproject.api.dto.CategoryDto;
import com.templateproject.api.dto.SerieDto;
import com.templateproject.api.entity.Category;
import com.templateproject.api.entity.Serie;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DtoConversionService {

    public SerieDto convertToSerieDto(Serie serie) {
        SerieDto serieDto = new SerieDto();
        serieDto.setId(serie.getId());
        serieDto.setName(serie.getName());
        serieDto.setProducer(serie.getProducer());
        serieDto.setPictureUrlXL(serie.getPictureUrlXL());
        serieDto.setPictureUrlXS(serie.getPictureUrlXS());
        serieDto.setTrailerURL(serie.getTrailerURL());
        serieDto.setReleaseDate(serie.getReleaseDate());
        serieDto.setDescription(serie.getDescription());
        serieDto.setIsCompleted(serie.getIsCompleted());
        serieDto.setCategories(serie.getCategories().stream().map(this::convertToCategoryDto).collect(Collectors.toList()));

        return serieDto;
    }

    public SerieDto lighterSerieDto(Serie serie) {
        SerieDto serieDto = new SerieDto();
        serieDto.setId(serie.getId());
        serieDto.setName(serie.getName());
        serieDto.setProducer(serie.getProducer());
        serieDto.setPictureUrlXL(serie.getPictureUrlXL());
        serieDto.setPictureUrlXS(serie.getPictureUrlXS());
        serieDto.setTrailerURL(serie.getTrailerURL());
        serieDto.setReleaseDate(serie.getReleaseDate());
        serieDto.setDescription(serie.getDescription());
        serieDto.setIsCompleted(serie.getIsCompleted());

        return serieDto;
    }
    public CategoryDto convertToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setSeries(category.getSeries().stream().map(this::lighterSerieDto).collect(Collectors.toList()));

        return categoryDto;
    }
}
