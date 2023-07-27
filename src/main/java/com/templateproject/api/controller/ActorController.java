package com.templateproject.api.controller;

import com.templateproject.api.dto.ActorDto;
import com.templateproject.api.entity.Actor;
import com.templateproject.api.repository.ActorRepository;
import com.templateproject.api.service.DtoConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorRepository actorRepository;
    private final DtoConversionService dtoConversionService;

    public ActorController(ActorRepository actorRepository, DtoConversionService dtoConversion) {
        this.actorRepository = actorRepository;
        this.dtoConversionService = dtoConversion;
    }

    @GetMapping("")
    public List<Actor> getActors() {
        return this.actorRepository.findAll();
    }
    @GetMapping("/search-actors")
    public List<ActorDto> searchActors(@RequestParam String fullName) {
        List<Actor> actors = this.actorRepository.findByFullNameContaining(fullName);
        return actors.stream().map(dtoConversionService::convertToActorDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ActorDto getActor(@PathVariable UUID id) {
        Actor actor = this.actorRepository.findById(id).orElse(null);
        return this.dtoConversionService.convertToActorDto(actor);
    }

    @PostMapping("")
    public ActorDto createActor(@RequestBody ActorDto actorDto) {
        Actor existingActor = actorRepository.findByFirstNameAndLastName(actorDto.getFirstName(), actorDto.getLastName());
        if (existingActor!= null) {
            return this.dtoConversionService.convertToActorDto(existingActor);
        }
        Actor newActor = new Actor(actorDto.getFirstName(), actorDto.getLastName(), actorDto.getPictureUrl());
        return this.dtoConversionService.convertToActorDto(actorRepository.save(newActor));
    }

    @PutMapping("/{id}")
    public ActorDto updateActor(@PathVariable UUID id, @RequestBody ActorDto actorDto) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor != null) {
            actor.setFirstName(actorDto.getFirstName());
            actor.setLastName(actorDto.getLastName());
            actor.setPictureUrl(actorDto.getPictureUrl());
            return this.dtoConversionService.convertToActorDto(actorRepository.save(actor));
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable UUID id) {
        actorRepository.deleteById(id);
    }
}
