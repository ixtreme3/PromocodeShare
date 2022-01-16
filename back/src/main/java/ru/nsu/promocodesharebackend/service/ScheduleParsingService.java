package ru.nsu.promocodesharebackend.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.config.ParserConfigs;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.parser.Parser;
import ru.nsu.promocodesharebackend.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.List;

@Service
@Profile("!test")
public class ScheduleParsingService {
    private final Parser parser;
    private final ShopService shopService;
    private final CouponService couponService;
    private final ParserConfigs parserConfigs;
    private final ShopRepository shopRepository;

    static final Logger log =
            LoggerFactory.getLogger(ScheduleParsingService.class);

    @Autowired
    public ScheduleParsingService(
            Parser parser,
            ShopService shopService,
            CouponService couponService,
            ParserConfigs parserConfigs,
            ShopRepository shopRepository) {
        this.parser = parser;
        this.shopService = shopService;
        this.couponService = couponService;
        this.parserConfigs = parserConfigs;
        this.shopRepository = shopRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    //@Scheduled(cron = "0 */2 * * * *")
    //cron: second, minute, hour, day, month, weekday (* - every)
    public void startParsingTask(){

        fillShopsData();

        fillCouponsData();

    }


    @Transactional
    public void fillShopsData() {
        log.info("Start shops parsing task...");

        var parsedShops = parser.parsePikabuShops(parserConfigs.getCategoriesURL(),
                                                        parserConfigs.getShopsURL());
        shopService.updateParsedShops(parsedShops);

        log.info("End shops parsing task.");


    }

    private void fillCouponsData() {
        if (!shopService.getShops().isEmpty()) {
            log.info("Start coupons parsing task...");

            List<Shop> shops = shopService.getShops();

            int maxCounter = shops.size()+1;
            int counter = 1;

            for (Shop shop: shops) {
                log.info("[PARSER]:"+ counter+"/"+maxCounter +" Parsing pikabu "+ shop.getName() +" shop...");
                counter++;

                getAndSampleCouponsFromShop(shop);
            }

            log.info("End coupons parsing task.");
        }
    }

    @Transactional
    public void getAndSampleCouponsFromShop(Shop shop){
        AbstractMap.SimpleEntry<List<Coupon>, String> parsingResult = parser.parsePikabuCouponsAndShopImageUrl(shop);
        couponService.updateParsedCoupons(parsingResult.getKey());
        shopService.updateParsedShopImageUrl(shop, parsingResult.getValue());
    }
}