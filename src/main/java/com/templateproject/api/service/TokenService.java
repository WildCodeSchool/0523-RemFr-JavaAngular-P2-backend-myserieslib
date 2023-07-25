package com.templateproject.api.service;
import com.templateproject.api.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateTokenRetrievePassword(String mail) {
        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        Instant now = Instant.now();
        JwtClaimsSet payload = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(mail)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(header, payload)).getTokenValue();
    }
    public String generateToken(Authentication auth) {
        JwsHeader header = JwsHeader.with(() -> "HS256").build();

        String scope = auth.getAuthorities().stream()
                .map((authority) -> authority.getAuthority())
                .collect(Collectors.joining(" "));

        User userOrigin = (User) auth.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet payload = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userOrigin.getId().toString())
                .claim("email", userOrigin.getEmail())
                .claim("scope", scope)
                .claim("picture", userOrigin.getPictureUrl())
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(header, payload)).getTokenValue();
    }
}
