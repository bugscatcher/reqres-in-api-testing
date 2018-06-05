package com.github.bugscatcher.dto.resources;

public class ResourceDTO {
    private String id;
    private String name;
    private String year;
    private String color;
    private String pantone_value;

    public String getId() {
        return id;
    }

    public ResourceDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ResourceDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getYear() {
        return year;
    }

    public ResourceDTO setYear(String year) {
        this.year = year;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ResourceDTO setColor(String color) {
        this.color = color;
        return this;
    }

    public String getPantone_value() {
        return pantone_value;
    }

    public ResourceDTO setPantone_value(String pantone_value) {
        this.pantone_value = pantone_value;
        return this;
    }
}
