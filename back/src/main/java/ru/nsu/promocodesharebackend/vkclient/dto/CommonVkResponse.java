package ru.nsu.promocodesharebackend.vkclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonVkResponse<T> {

    @JsonProperty("response")
    private T response;

    public T getData() {
        if (response == null) {
            throw new RuntimeException("в ответе от ВК отсутствует поле response");
        }
        return response;
    }
}
