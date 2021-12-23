package ru.nsu.promocodesharebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.promocodesharebackend.model.User;
import ru.nsu.promocodesharebackend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    final
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/add")
    public void addNewUser(@RequestBody User newUser){ userRepository.save(newUser); }

    @PutMapping(path = "/update")
    public void updateUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @DeleteMapping(path = "/delete")
    public void deleteUser(@RequestBody User user) {
        userRepository.delete(user);
    }
}
