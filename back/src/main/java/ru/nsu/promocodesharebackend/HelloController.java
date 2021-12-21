package ru.nsu.promocodesharebackend;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    String getHello(@AuthenticationPrincipal OAuth2User user) {
        var name = user != null ? user.getName() : "неизвестный пользователь";
        return "Привет, " + name;
    }
}
