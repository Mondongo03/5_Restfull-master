package com.example.dades;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String fullName;

    public UserDto() {
    }

    public UserDto(Integer id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public UserDto(User user) {
        id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }
}
