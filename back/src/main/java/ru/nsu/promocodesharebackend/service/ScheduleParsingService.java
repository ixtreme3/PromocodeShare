package ru.nsu.promocodesharebackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.PromocodeShareBackendApplication;
import ru.nsu.promocodesharebackend.config.ParserConfigs;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.parser.Parser;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Profile("!test")
public class ScheduleParsingService {
    private final Parser parser;
    private final ShopService shopService;
    private final CouponService couponService;
    private final ParserConfigs parserConfigs;

    static final Logger log =
            LoggerFactory.getLogger(ScheduleParsingService.class);

    @Autowired
    public ScheduleParsingService(
            Parser parser,
            ShopService shopService,
            CouponService couponService,
            ParserConfigs parserConfigs) {
        this.parser = parser;
        this.shopService = shopService;
        this.couponService = couponService;
        this.parserConfigs = parserConfigs;
    }

    @EventListener(ApplicationReadyEvent.class)
    //@Scheduled(cron = "0 */2 * * * *")
    //cron: second, minute, hour, day, month, weekday (* - every)
    @Transactional
    public void fillShopsData() {
        log.info("Start shops parsing task...");
        //log.info(LocalDateTime.now().toString());

        //shopService.clearAll();
        var shops = parser.parsePikabuShops(parserConfigs.getCategoriesURL(),
                                                        parserConfigs.getShopsURL());
        shopService.saveShops(shops);

        log.info("End shops parsing task.");


        fillCouponsData();

    }

    public void fillCouponsData() {
        if (!shopService.getShops().isEmpty()) {
            log.info("Start coupons parsing task...");

            List<Shop> shops = shopService.getShops();

            var coupons = parser.parsePikabuCoupons(shops);
            couponService.saveCoupons(coupons);
            log.info("End coupons parsing task.");
        }

    }
}