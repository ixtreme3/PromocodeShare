package ru.nsu.promocodesharebackend.vkclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VkUserInfoResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    public VkUserInfoResponse(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public VkUserInfoResponse() {}

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
