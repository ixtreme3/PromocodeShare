package ru.nsu.promocodesharebackend.security.vk;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public final class VKAccessTokenClient
		implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

	private static final Logger log = LoggerFactory.getLogger(VKAccessTokenClient.class);

	private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";

	private final RestTemplate rest;

	public VKAccessTokenClient(RestTemplate rest) {
		this.rest = rest;
	}

	@Override
	public OAuth2AccessTokenResponse getTokenResponse(
			OAuth2AuthorizationCodeGrantRequest req) {
		Assert.notNull(req, "req cannot be null");
		var response = execute(prepareRequestEntity(req));
		log.info("Получен access token для vk: {}", response.getAccessToken().getTokenValue());
		return withScopes(response, req.getClientRegistration().getScopes());
	}

	private OAuth2AccessTokenResponse withScopes(OAuth2AccessTokenResponse res, Set<String> scopes) {
		return OAuth2AccessTokenResponse
				.withResponse(res)
				.scopes(scopes)
				.build();
	}

	private RequestEntity<?> prepareRequestEntity(OAuth2AuthorizationCodeGrantRequest req) {
		var client = req.getClientRegistration();
		var uri = fromUriString(client.getProviderDetails().getTokenUri())
				.queryParam("code", req.getAuthorizationExchange().getAuthorizationResponse().getCode())
				.queryParam("redirect_uri", req.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri())
				.queryParam("client_id", client.getClientId())
				.queryParam("client_secret", client.getClientSecret())
				.build().toUri();
		return new RequestEntity<>(HttpMethod.GET, uri);
	}

	private OAuth2AccessTokenResponse execute(RequestEntity<?> request) {
		try {
			var response = this.rest.exchange(request, VkTokenResponse.class);
			var body = response.getBody();
			Assert.notNull(body, "access token response body is null");
			var additional = new HashMap<String, Object>();
			additional.put("user_id", body.getUserId());
			return OAuth2AccessTokenResponse
					.withToken(body.getAccessToken())
					.expiresIn(body.getExpiresIn())
					.additionalParameters(additional)
					.tokenType(BEARER)
					.build();
		}
		catch (RestClientException ex) {
			var error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
					"An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
							+ ex.getMessage(), null);
			throw new OAuth2AuthorizationException(error, ex);
		}
	}

}
