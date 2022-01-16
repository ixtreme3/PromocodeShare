package ru.nsu.promocodesharebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.promocodesharebackend.model.Coupon;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCodeAndName(String code, String name);
}
