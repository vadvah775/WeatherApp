package com.weatherApp.dto;

import lombok.Getter;

@Getter
public class CurrentUserDto {
    private String login;

    public CurrentUserDto(String login) {
        this.login = login;
    }
}
