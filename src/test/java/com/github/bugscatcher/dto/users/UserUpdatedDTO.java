package com.github.bugscatcher.dto.users;

public class UserUpdatedDTO extends UserNewDTO {
    private String updatedAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public UserUpdatedDTO setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
