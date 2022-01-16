package ru.nsu.promocodesharebackend.security.vk;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.vkclient.VkClient;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class VkOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private static final String NAME_ATTRIBUTE = "name";

	private final VkClient vkClient;

	public VkOAuth2UserService(VkClient vkClient) {
		this.vkClient = vkClient;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
		Set<GrantedAuthority> authorities = new LinkedHashSet<>();
		OAuth2AccessToken token = req.getAccessToken();
		for (String authority : token.getScopes()) {
			authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
		}

		var userId = (String) req.getAdditionalParameters().get("user_id");
		var userInfo = vkClient.getUserInfo(userId, req.getAccessToken().getTokenValue());
		var userParams = new HashMap<String, Object>();
		userParams.put(NAME_ATTRIBUTE, userInfo.getLastName() + " " + userInfo.getFirstName());

		return new DefaultOAuth2User(authorities, userParams, NAME_ATTRIBUTE);
	}
}