package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.enums.ProfileRole;

@Getter
@Setter
public class JwtDTO {
    private String id;
    private String username;
    private ProfileRole role;

    public JwtDTO(String id, String userName, ProfileRole role) {
        this.id = id;
        this.username = userName;
        this.role = role;
    }

    public JwtDTO(String id) {
        this.id = id;
    }
}
