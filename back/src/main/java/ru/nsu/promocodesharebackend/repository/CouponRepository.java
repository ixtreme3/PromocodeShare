package ru.nsu.promocodesharebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.promocodesharebackend.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
