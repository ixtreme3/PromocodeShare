package ru.nsu.promocodesharebackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.promocodesharebackend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
