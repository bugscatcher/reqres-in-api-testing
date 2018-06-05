package com.github.bugscatcher.dto.users;

import com.github.bugscatcher.dto.PageDTO;

import java.util.Collection;

public class UsersListDTO extends PageDTO {
    private Collection<UserDTO> data;

    public Collection<UserDTO> getData() {
        return data;
    }

    public UsersListDTO setData(Collection<UserDTO> data) {
        this.data = data;
        return this;
    }
}
