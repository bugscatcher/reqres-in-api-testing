package com.github.bugscatcher.dto.resources;

public class ResourceSingleDTO {
    private ResourceDTO data;

    public ResourceDTO getData() {
        return data;
    }

    public ResourceSingleDTO setData(ResourceDTO data) {
        this.data = data;
        return this;
    }
}
