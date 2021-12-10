package ru.nsu.promocodesharebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.promocodesharebackend.DTO.CouponDTO;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.repository.CouponRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository){
        this.couponRepository = couponRepository;
    }

    public void clearAll() {
        System.out.println("Start clearing...");
        couponRepository.deleteAll();
        System.out.println("Clearing completed.");
    }

    public void saveCoupons(List<Coupon> coupons){
        if (coupons != null) {
            couponRepository.saveAll(coupons);
            couponRepository.flush();
        }
    }

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public List<Coupon> findAllPaged(Integer page, Integer entitiesPerPage) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> query = builder.createQuery(Coupon.class);
        Root<Coupon> root = query.from(Coupon.class);

        //query.orderBy(builder.asc(root.get("name")));
        query.where(builder.equal(root.get("isDeleted"), false));

        query.distinct(true);
        List<Coupon> result = em
                .createQuery(query.select(root))
                .setFirstResult((page - 1) * entitiesPerPage)//страница начинается с первой
                .setMaxResults(entitiesPerPage)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        return result;
    }

    public Coupon findById(Long id) {
        return couponRepository.findById(id).get();
    }

    public Coupon update(Long id, CouponDTO newCouponDTO) {
        Optional<Coupon> storedCouponOptional = couponRepository.findById(id);
        if (storedCouponOptional.isEmpty()) {
            return null;
        }
      Coupon storedCoupon = storedCouponOptional.get();
//        Coupon storedCoupon = couponRepository.getById(id);;
        //storedCoupon.setShop(newCoupon.getShop());
        //storedCoupon.setCode(newCoupon.getCode());
        storedCoupon.setName(newCouponDTO.getName());
        //storedCoupon.setUser(newCoupon.getUser());
        storedCoupon.setDescription(newCouponDTO.getDescription());
        storedCoupon.setExpirationDate(newCouponDTO.getExpirationDate());
        //storedCoupon.setIsArchive(newCoupon.getIsArchive());
        //storedCoupon.setIsDeleted(newCoupon.getIsDeleted());

        return  couponRepository.save(storedCoupon);
    }

    public void deleteById(Long id) {
        couponRepository.deleteById(id);
    }
}
