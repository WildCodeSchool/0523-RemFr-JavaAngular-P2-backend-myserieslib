package com.templateproject.api.dto;

import com.templateproject.api.entity.User;

public record LoginResponse(String token, User user) {
}