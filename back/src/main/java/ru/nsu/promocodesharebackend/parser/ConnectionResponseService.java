package ru.nsu.promocodesharebackend.parser;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.nsu.promocodesharebackend.config.ParserConfigs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionResponseService {
    private int connectionCounter = 0;
    private final int maxConnectionCounter = 5;
    private Connection.Response shopConnectionResponse;

    /*@Autowired
    private final ParserConfigs parserConfigs*/;

    private String token;

    @Autowired
    public ConnectionResponseService(ParserConfigs parserConfigs) {
        //this.parserConfigs = _parserConfigs;
        try {
            shopConnectionResponse = Jsoup
                    .connect(parserConfigs.getShopsURL())
                    .userAgent("Mozilla")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Connection.Response executeConnection(String fullHref,
                                                 String shopHref,
                                                 Connection.Method method)
            throws IOException, InterruptedException
    {
        //System.out.println("Method: "+ method);
        boolean moveToNext = false;
        while (!moveToNext)
            try {
                shopConnectionResponse = executeConnectionWithToken(fullHref, shopHref, token, method, shopConnectionResponse.cookies());
                shopConnectionResponse.bufferUp();
                moveToNext = true;
            } catch (HttpStatusException e) {
                //System.err.println(e.getStatusCode());
                if (e.getStatusCode() == 429) { //слишком частые запросы
                    System.err.println(e.getMessage() + " "+ HttpStatus.TOO_MANY_REQUESTS + ", reconnecting...");
                    Thread.sleep((long) (10_000));
                } else { //бывает 404
                    e.printStackTrace();
                    moveToNext = true;
                }
            }
        updateToken();
            /*connectionCounter++;

            if (connectionCounter > maxConnectionCounter){
                updateToken();
                connectionCounter = 0;
            }*/
        return shopConnectionResponse;
    }

    private void updateToken(){
        token = shopConnectionResponse
                .cookies()
                .get("XSRF-TOKEN")
                .split("%")[0] + "=";
    }

    private Connection.Response executeConnectionWithToken(String fullHref,
                                                           String shopHref,
                                                           String token,
                                                           Connection.Method method,
                                                           Map<String, String> cookies)
            throws IOException
    {
        return Jsoup
                .connect(fullHref)
                .method(method)
                .headers(getCouponRequestHeaders(token, shopHref))
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();
    }

    private Map<String, String> getCouponRequestHeaders(String token, String shopRefer) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        headers.put("X-XSRF-TOKEN", token);
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("Origin", "https://promokod.pikabu.ru");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Referer", shopRefer);
        headers.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");

        return headers;
    }


}
