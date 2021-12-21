package ru.nsu.promocodesharebackend.shedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.service.CouponService;

import java.util.Date;
import java.util.List;

@Component
public class SheduleForExpiredCupon {
    private final CouponService couponService;

    public SheduleForExpiredCupon(CouponService couponService) {
        this.couponService = couponService;
    }

    @Scheduled(cron = "0 30 1 * * *", zone = "Asia/Novosibirsk")
    public void expiredCupon() {
        List<Coupon> coupons = couponService.findAll();
        Date date = new Date();

        for (Coupon coupon : coupons) {
            if (coupon.getExpirationDate().before(date)) {
                coupon.setArchive(true);
            }
        }

        couponService.saveCoupons(coupons);
    }


}
