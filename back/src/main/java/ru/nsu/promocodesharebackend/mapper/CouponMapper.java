package ru.nsu.promocodesharebackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.DTO.CouponDTO;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.repository.CouponRepository;
import ru.nsu.promocodesharebackend.repository.ShopRepository;

@Service
public class CouponMapper {

    private final ShopRepository shopRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CouponMapper(ShopRepository shopRepository,
                        CouponRepository couponRepository) {
        this.shopRepository = shopRepository;
        this.couponRepository = couponRepository;
    }

    public CouponDTO fromEntity(Coupon coupon){
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setShopId(coupon.getShop().getId());
        couponDTO.setName(coupon.getName());
        couponDTO.setCode(coupon.getCode());
        couponDTO.setDescription(coupon.getDescription());
        couponDTO.setExpirationDate(coupon.getExpirationDate());
        couponDTO.setUserId(coupon.getUser() != null ? coupon.getUser().getId() : null);
        couponDTO.setIsArchive(coupon.getIsArchive());
        couponDTO.setIsDeleted(coupon.getIsDeleted());

        return couponDTO;
    }

    public Coupon toEntity(CouponDTO couponDTO){
        Coupon coupon = new Coupon();
        Shop shop = shopRepository.findById(couponDTO.getShopId()).get();
        coupon.setShop(shop);
        coupon.setName(couponDTO.getName());
        coupon.setCode(couponDTO.getCode());
        coupon.setDescription(couponDTO.getDescription());
        coupon.setExpirationDate(couponDTO.getExpirationDate());
        coupon.setUser(null);
        coupon.setIsArchive(false);
        coupon.setIsDeleted(false);

        return coupon;
    }
}
