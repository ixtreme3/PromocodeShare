package ru.nsu.promocodesharebackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.model.User;
import ru.nsu.promocodesharebackend.repository.CouponRepository;
import ru.nsu.promocodesharebackend.repository.ShopRepository;
import ru.nsu.promocodesharebackend.repository.UserRepository;
import ru.nsu.promocodesharebackend.DTO.CouponDTO;

import java.util.Optional;

@Service
public class CouponMapper {

    private final ShopRepository shopRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Autowired
    public CouponMapper(ShopRepository shopRepository,
                        CouponRepository couponRepository,
                        UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
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
        Long userId = couponDTO.getUserId();
        Optional<User> userOptional = Optional.empty();
        if (userId != null) {
            userOptional = userRepository.findById(userId);
        }
        coupon.setUser(userOptional.isPresent() ? userOptional.get() : null);
        coupon.setIsArchive(couponDTO.getIsArchive() != null ? couponDTO.getIsArchive() : false);
        coupon.setIsDeleted(couponDTO.getIsDeleted() != null ? couponDTO.getIsDeleted() : false);

        return coupon;
    }
}
