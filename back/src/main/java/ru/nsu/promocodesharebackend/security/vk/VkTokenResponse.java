package ru.nsu.promocodesharebackend.security.vk;

import com.fasterxml.jackson.annotation.JsonProperty;

class VkTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@JsonProperty("user_id")
	private String userId;

	public VkTokenResponse() {}

	public VkTokenResponse(String accessToken, Integer expiresIn, String userId) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public String getUserId() {
		return userId;
	}

}