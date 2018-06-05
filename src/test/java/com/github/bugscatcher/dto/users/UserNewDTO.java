package com.github.bugscatcher.dto.users;

public class UserNewDTO {
    private String name;
    private String job;

    public String getName() {
        return name;
    }

    public UserNewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getJob() {
        return job;
    }

    public UserNewDTO setJob(String job) {
        this.job = job;
        return this;
    }
}
