package com.github.bugscatcher.dto.users;

public class UserDTO {
    private String id;
    private String first_name;
    private String last_name;
    private String avatar;

    public String getId() {
        return id;
    }

    public UserDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public UserDTO setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public UserDTO setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserDTO setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
