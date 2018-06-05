package com.github.bugscatcher.dto.users;

public class UserCreatedDTO extends UserNewDTO {
    private String id;
    private String createdAt;

    public String getId() {
        return id;
    }

    public UserCreatedDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public UserCreatedDTO setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
