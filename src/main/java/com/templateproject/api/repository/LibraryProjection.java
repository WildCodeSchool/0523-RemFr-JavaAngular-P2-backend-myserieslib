package com.templateproject.api.repository;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.User;

import java.util.UUID;

public interface LibraryProjection {
    String getUserNickname();
    String getComment();
    String getScore();
    UUID getId();

    String getSerieName();

}