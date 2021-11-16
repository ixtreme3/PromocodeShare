package ru.nsu.promocodesharebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.repository.ShopRepository;

import java.util.List;

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

    public List<Shop> getShops() {
        return shopRepository.findAll();
    }
}
