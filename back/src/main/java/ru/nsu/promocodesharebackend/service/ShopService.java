package ru.nsu.promocodesharebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.repository.ShopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    public void clearAll() {
        System.out.println("Start clearing...");
        shopRepository.deleteAll();
        System.out.println("Clearing completed.");
    }

    public void saveShops(List<Shop> shops){
        if (shops != null) {
            shopRepository.saveAll(shops);
            shopRepository.flush();
        }
    }

    public void updateParsedShops(List<Shop> parsedShops){
        for (Shop parsedShop : parsedShops) {
            var storedShopOptional = shopRepository.findByHref(parsedShop.getHref());
            if (storedShopOptional.isPresent()){
                Shop storedShop = storedShopOptional.get();
                storedShop.setCategory(parsedShop.getCategory());
                storedShop.setName(parsedShop.getName());
                storedShop.setTitle(parsedShop.getTitle());
                shopRepository.save(storedShop);
            } else {
                shopRepository.save(parsedShop);
            }

        }
    }

    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    public void updateParsedShopImageUrl(Shop shop, String shopImageUrl) {
        var storedShopOptional = shopRepository.findByHref(shop.getHref());
        if (storedShopOptional.isPresent()){
            Shop storedShop = storedShopOptional.get();
            storedShop.setImageURL(shopImageUrl);
            shopRepository.save(storedShop);
        }
    }
}
