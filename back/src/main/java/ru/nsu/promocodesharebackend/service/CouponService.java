package ru.nsu.promocodesharebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.User;
import ru.nsu.promocodesharebackend.repository.CouponRepository;

import java.util.List;

@Service
public class CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository){
        this.couponRepository = couponRepository;
    }


    public void clearAll() {
        System.out.println("Start clearing...");
        couponRepository.deleteAll();
        System.out.println("Clearing completed.");
    }

    public void saveCoupons(List<Coupon> coupons){
        if (coupons != null) {
            couponRepository.saveAll(coupons);
            couponRepository.flush();
        }
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }
}
