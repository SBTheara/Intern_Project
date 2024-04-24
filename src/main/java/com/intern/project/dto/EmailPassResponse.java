package com.intern.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailPassResponse {

    private String accessToken;
    private String refreshToken;
    private String issuedAt;
    private String expireAt;
}
