package com.github.bugscatcher.dto.resources;

import com.github.bugscatcher.dto.PageDTO;

import java.util.Collection;

public class ResourcesListDTO extends PageDTO {
    private Collection<ResourceDTO> data;

    public Collection<ResourceDTO> getData() {
        return data;
    }

    public ResourcesListDTO setData(Collection<ResourceDTO> data) {
        this.data = data;
        return this;
    }
}
