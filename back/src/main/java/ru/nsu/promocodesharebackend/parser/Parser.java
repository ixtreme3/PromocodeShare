package ru.nsu.promocodesharebackend.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;

import java.io.IOException;
import java.util.*;

@Component
public class Parser {

    static final Logger log =
            LoggerFactory.getLogger(Parser.class);

    //private PikabuShopsConnection pikabuShopsConnection;

    @Autowired
    private ConnectionResponseService connectionResponseService;

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

        return  shopsMap.values().stream().toList();
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


    private Element getCategoriesTable(Document page)  {
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
                /*try {
                    Thread.sleep((long)(Math.random() * 1000));// сайт ругается на слишком большое количество запросов
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Document shopPage = Jsoup.connect(shop.getHref()).userAgent("Mozilla").get();
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


    public AbstractMap.SimpleEntry<List<Coupon>, String> parsePikabuCouponsAndShopImageUrl(Shop shop) {
        List<Coupon> coupons = new ArrayList<>();
        String shopImageUrl = null;
        try {

            Document shopPage = connectionResponseService
                    .executeConnection(
                            shop.getHref(),
                            shop.getHref(),
                            Connection.Method.GET)
                    .parse();

            shopImageUrl = shopPage
                    .selectFirst("div[class=\"right-content\"]")
                    .selectFirst("img[class=\"lazyload\"]")
                    .attr("src");

            Elements promocodesElements = shopPage
                    .selectFirst("div[class=\"tovars\"]")
                    .select("article[class=\"item-tovars\"]");

            for (Element promocodeElement : promocodesElements) {

                String promocodeType = promocodeElement
                        .selectFirst("div[class=\"open-tovar\"]")
                        .selectFirst("a")
                        .attr("class");


                // это промокод, а не акция
                if (promocodeType.equals("o open-coupon")) {

                    /**
                     * Get coupon code
                     */
                    String promocodeHrefAdd = promocodeElement //href="#cSboaRfsoRkp1", need "SboaRfsoRkp1"
                            .selectFirst("div[class=\"open-tovar\"]")
                            .selectFirst("a")
                            .attr("href")
                            .substring(2);
                    String shopTempHref = shop.getHref().replace("shops", "coupon");

                    String couponResponse = connectionResponseService
                            .executeConnection(
                                    shopTempHref +"/"+ promocodeHrefAdd,
                                    shop.getHref(),
                                    Connection.Method.POST).body();

                    JSONObject couponJSON;
                    try { //404 ошибка с пустым json-ответом
                        couponJSON = new JSONObject(couponResponse);
                    } catch (JSONException e) {
                        System.err.println(e.getMessage());
                        continue;
                    }
                    String couponCode =  couponJSON.getString("promocode");

                    /** Expiration date */
                    String[] expirationDateText = promocodeElement
                            .selectFirst("div[class=\"tovav-content\"]")
                            .selectFirst("p[class=\"data\"]")
                            .text()
                            .split(" ");
                    String[] expirationDateStrings = expirationDateText[expirationDateText.length - 1]
                            .split("\\.");

                    int day = Integer.parseInt(expirationDateStrings[0]) ; //A year y is represented by the integer y - 1900.
                    int month = Integer.parseInt(expirationDateStrings[1]) - 1;//Calendar.January = 0
                    int year = Integer.parseInt(expirationDateStrings[2]) - 1900;
                    Date expirationDate = new Date(year, month, day);

                    /** Coupon name */
                    String couponName = promocodeElement
                            .selectFirst("div[class=\"tovav-content\"]")
                            .selectFirst("a[class=\"click-coupon o\"]")
                            .text();

                    /** Coupon description */
                    String couponDescription = promocodeElement
                            .selectFirst("div[class=\"tovav-content\"]")
                            .selectFirst("p:not([class])")
                            .text();

                    /** Coupon */
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
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.info("No active coupons");
        }

        //log.info("Done");
        return new AbstractMap.SimpleEntry<>(coupons, shopImageUrl);
    }


    public void testCouponCode() {
        try {
                /*try {
                    Thread.sleep((long)(Math.random() * 1000));// сайт ругается на слишком большое количество запросов
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            Document shopPage = Jsoup.connect("https://promokod.pikabu.ru/shops/mvideo#cSboaRfsoRkp1").userAgent("Mozilla").get();
            Element code = shopPage
                    .selectFirst("a[href=\"#cSboaRfsoRkp1\"][class=\"o open-coupon\"]");
                    //.selectFirst("span[class=\"stars\"]");

            System.out.println(code.toString());


        }
        //nullPointer из shopPage.selectFirst("div[class=\"tovars\"]"),
        // т.к. может не быть активных купонов
        catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.info("No active coupons");
        }
    }



}

