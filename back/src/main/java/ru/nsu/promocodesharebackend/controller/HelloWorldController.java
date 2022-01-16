package ru.nsu.promocodesharebackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    /* никогда не сработает, так как где-то в
     * WebMvcAutoConfiguration$EnableWebMvcConfiguration.class есть стандартный
     * HelloController#getHello(OAuth2User)
    */
    @RequestMapping("/")
    public String helloWorld(){
        return "Hello World from Spring Boot";
    }
}