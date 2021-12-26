package ru.nsu.promocodesharebackend.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import ru.nsu.promocodesharebackend.security.vk.VKAccessTokenClient;
import ru.nsu.promocodesharebackend.security.vk.VkOAuth2UserService;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final VKAccessTokenClient accessTokenClient;
    private final VkOAuth2UserService userService;
    public SecurityConfig(VKAccessTokenClient accessTokenClient, VkOAuth2UserService userService) {
        this.accessTokenClient = accessTokenClient;
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/", "/coupon/{id}", "/coupon").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED))
                )
                .oauth2Login()
                    .tokenEndpoint().accessTokenResponseClient(accessTokenClient)
                    .and()
                    .userInfoEndpoint().userService(userService);
    }

}