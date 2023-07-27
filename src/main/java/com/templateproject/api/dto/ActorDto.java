package com.templateproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String pictureUrl;
}
