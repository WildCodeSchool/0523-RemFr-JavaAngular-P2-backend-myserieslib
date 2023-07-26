package com.templateproject.api.repository;

import com.templateproject.api.entity.History;
import com.templateproject.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    List<History> findByUserId(UUID userId);
}
