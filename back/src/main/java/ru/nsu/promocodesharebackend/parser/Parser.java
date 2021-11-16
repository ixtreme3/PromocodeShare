package ru.nsu.promocodesharebackend.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.PromocodeShareBackendApplication;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Parser {

    static final Logger log =
            LoggerFactory.getLogger(Parser.class);

    public List<Shop> parsePikabuShops(String categoriesURL , String shopsURL){

        Map<String, Shop> shopsMap = new HashMap<>(); //href & Shop для быстого поиска по ссылке
        try {
            log.info("[PARSER]: Getting pikabu categories page...");
            Document categoriesPage = Jsoup.connect(categoriesURL).get();
            log.info("[PARSER]: Pikabu categories page received");

            log.info("[PARSER]: Getting pikabu shops page...");
            Document shopsPage = Jsoup.connect(shopsURL).get();
            log.info("[PARSER]: Pikabu shops page received");

            log.info("[PARSER]: Parsing pikabu categories page for shops...");
            parseShopsFromCategoriesPikabu(categoriesPage, shopsMap);
            log.info("[PARSER]: Pikabu categories page parsed for shops");

            log.info("[PARSER]: Parsing pikabu shops page for shops...");
            parseShopsFromShopsPikabu(shopsPage, shopsMap);
            log.info("[PARSER]: Pikabu shops page parsed for shops");

        } catch (IOException e){
            e.printStackTrace();
        }

        List<Shop> shops = shopsMap.values().stream().toList();
        return  shops;
    }

    private void parseShopsFromCategoriesPikabu(Document page, Map<String, Shop> shopsMap) {
        try {
            Elements categoriesElementsList = page
                    .selectFirst("div[class=\"promocod-home catalog-list\"]")
                    .selectFirst("div[class=\"row\"]")
                    .select("a");

            for (Element categoryElement : categoriesElementsList) { //пробегаемся по категориям
                String categoryName = categoryElement.attr("title");
                String categoryHref = categoryElement.attr("href");

                log.info("[PARSER]: Getting  pikabu category \""+categoryName+"\" page...");
                Document categoryPage = Jsoup.connect(categoryHref).get();
                log.info("[PARSER]: Pikabu category page \""+categoryName+"\" received");

                log.info("[PARSER]: Parsing \""+categoryName+"\"...");
                Elements shopsElementsList = categoryPage
                        .selectFirst("div[class=\"topshop-home\"]")
                        .select("a");

                for (Element shopElement : shopsElementsList) {//парсим магазины из каждой категории
                    String shopName = shopElement.text();
                    String shopHref = shopElement.attr("href");

                    Shop shop = new Shop();
                    shop.setName(shopName);
                    shop.setTitle(shopName);
                    shop.setHref(shopHref);
                    shop.setCategory(categoryName);

                    shopsMap.put(shopHref, shop);
                }
                log.info("[PARSER]: Done");

            }
        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void parseShopsFromShopsPikabu(Document page, Map<String, Shop> shopsMap) {
        Elements shopsElementsList = page
                .selectFirst("div[class=\"categories-list\"]")
                .select("a");

        //пробегаемся по магазинам и вставляем английские заголовки (если есть)
        for (Element shopElement : shopsElementsList) {
            String shopTitle = shopElement.attr("title");
            String shopHref = shopElement.attr("href");

            Shop tempShop;
            if ((tempShop = shopsMap.get(shopHref)) != null){
                tempShop.setTitle(shopTitle);
            }

        }
    }


    private Element getCategoriesTable(Document page) throws IOException {
        Element categoryListContainer = page.select("div[class=\"promocod-home catalog-list\"]").get(0);
        return categoryListContainer.selectFirst("div[class=\"row\"]");
    }


    public List<Coupon> parsePikabuCoupons(List<Shop> shops) {
        List<Coupon> coupons = new ArrayList<>();
        int counter = 1;
        int maxCounter = shops.size()+1;
        for (Shop shop: shops) {
           log.info("[PARSER]:"+ counter+"/"+maxCounter +" Parsing pikabu "+ shop.getName() +" shop...");
            counter++;
            try {
                Document shopPage = Jsoup.connect(shop.getHref()).get();
                Elements promocodesElements = shopPage
                        .selectFirst("div[class=\"tovars\"]")
                        .select("div[class=\"item-tovars\"]");
                for (Element promocodeElement: promocodesElements) {
                    String promocodeType = promocodeElement
                            .selectFirst("div[class=\"open-tovar\"]")
                            .selectFirst("a")
                            .attr("class");

                    if (promocodeType.equals("open-coupon ")){ // это промокод, а не акция
                        String couponCode = promocodeElement
                                .selectFirst("div[class=\"open-tovar\"]")
                                .selectFirst("div[class=\"clipboardjs-workaround\"]")
                                .text();

                        String[] expirationDateText = promocodeElement
                                .selectFirst("div[class=\"tovav-content\"]")
                                .selectFirst("p[class=\"data\"]")
                                .text()
                                .split(" ");
                        String[] expirationDateStrings = expirationDateText[expirationDateText.length-1]
                                .split("\\.");

                        int day =  Integer.parseInt(expirationDateStrings[0]);
                        int month = Integer.parseInt(expirationDateStrings[1]) - 1;//Calendar.January = 0
                        int year =  Integer.parseInt(expirationDateStrings[2]);

                        Date expirationDate = new Date(
                                year,
                                month,
                                day);

                        String couponName = promocodeElement
                                .selectFirst("div[class=\"tovav-content\"]")
                                .selectFirst("a[class=\"click-coupon\"]")
                                .text();

                        String couponDescription = promocodeElement
                                .selectFirst("div[class=\"tovav-content\"]")
                                .selectFirst("p")
                                .text();

                        Coupon coupon = new Coupon();

                        coupon.setUser(null);
                        coupon.setCode(couponCode);
                        coupon.setExpirationDate(expirationDate);
                        coupon.setDescription(couponDescription);
                        coupon.setName(couponName);
                        coupon.setShop(shop);
                        coupon.setIsArchive(false);
                        coupon.setIsDeleted(false);

                        coupons.add(coupon);
                    }
                }

            }
            //nullPointer из shopPage.selectFirst("div[class=\"tovars\"]"),
            // т.к. может не быть активных купонов
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                log.info("No active coupons");
            }
            //log.info("Done");
        }
        return coupons;
    }
}
