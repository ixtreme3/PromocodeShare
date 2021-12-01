package ru.nsu.promocodesharebackend.sheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.parser.Parser;
import ru.nsu.promocodesharebackend.service.CouponService;
import ru.nsu.promocodesharebackend.service.ShopService;

import java.util.List;

@Component
public class ShedulerForNewCupon {
    private final CouponService couponService;
    private final ShopService shopService;
    private final Parser parser;

    public ShedulerForNewCupon(CouponService couponService, ShopService shopService, Parser parser) {
        this.couponService = couponService;
        this.shopService = shopService;
        this.parser = parser;
    }

    @Scheduled(cron = "0 30 1 * * 0", zone = "Asia/Novosibirsk")
    public void newCupon() {
        if (!shopService.getShops().isEmpty()) {
            List<Shop> shops = shopService.getShops();
            var coupons = parser.parsePikabuCoupons(shops);
            List<Coupon> allCoupons = couponService.findAll();
            for (Coupon newCoupon : coupons) {
                boolean isNewCoupon = true;
                for(Coupon coupon : allCoupons){
                    if (newCoupon.getCode().equals(coupon.getCode())) {
                        isNewCoupon = false;
                        break;
                    }
                }
                if (isNewCoupon){
                    couponService.saveCoupon(newCoupon);
                }
            }
        }
    }
}
