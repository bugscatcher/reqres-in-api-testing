package com.github.bugscatcher.dto.users;

public class UserSingleDTO {
    private UserDTO data;

    public UserDTO getData() {
        return data;
    }

    public UserSingleDTO setData(UserDTO data) {
        this.data = data;
        return this;
    }
}
