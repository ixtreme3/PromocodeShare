package ru.nsu.promocodesharebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.promocodesharebackend.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {


}
