package ru.nsu.promocodesharebackend.vkclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.nsu.promocodesharebackend.vkclient.dto.CommonVkResponse;
import ru.nsu.promocodesharebackend.vkclient.dto.VkUserInfoResponse;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class VkClient {

    private static final String API_VERSION = "5.131";
    private static final String GET_USER_URL = "https://api.vk.com/method/users.get";
    private final RestTemplate rest;

    public VkClient(RestTemplate rest) {
        this.rest = rest;
    }

    public VkUserInfoResponse getUserInfo(String id, String accessToken) {
        var uri = fromUriString(GET_USER_URL)
                .queryParam("v", API_VERSION)
                .queryParam("access_token", accessToken)
                .queryParam("user_ids", id)
                .build().toUri();
        var request = new RequestEntity<>(GET, uri);
        var responseType = new ParameterizedTypeReference<CommonVkResponse<List<VkUserInfoResponse>>>() {};
        var response = rest.exchange(request, responseType);
        return response.getBody().getData().stream().findFirst().orElseThrow();
    }
}
