package ru.nsu.promocodesharebackend.service;

import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.model.User;
import ru.nsu.promocodesharebackend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        if (user == null){
            return;
        }
        userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
