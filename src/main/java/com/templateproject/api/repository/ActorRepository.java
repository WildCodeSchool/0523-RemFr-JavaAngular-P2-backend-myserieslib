package com.templateproject.api.repository;

import com.templateproject.api.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Actor findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT a FROM Actor a WHERE CONCAT(a.firstName, ' ', a.lastName) LIKE %?1%")
    List<Actor> findByFullNameContaining(String fullName);

}
