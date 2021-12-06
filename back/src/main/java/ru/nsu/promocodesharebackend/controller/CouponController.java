package ru.nsu.promocodesharebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.promocodesharebackend.DTO.CouponDTO;
import ru.nsu.promocodesharebackend.mapper.CouponMapper;
import ru.nsu.promocodesharebackend.model.Coupon;
import ru.nsu.promocodesharebackend.service.CouponService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService service;
    private final CouponMapper couponMapper;

    @Autowired
    public CouponController(CouponService service,
                            CouponMapper couponMapper) {
        this.service = service;
        this.couponMapper = couponMapper;
    }

    @GetMapping
    public List<Coupon> findAllPaged(@RequestParam(name ="page") Integer page,
                                     @RequestParam(name ="rowsPerPage") Integer entitiesPerPage)
    {
        //if (page.isPresent() && entitiesPerPage.isPresent())
            return service.findAllPaged(page, entitiesPerPage);
    }



    /*@GetMapping("/{id}")
    public Coupon findById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }*/

    @PostMapping
    public Coupon create(@Valid @RequestBody CouponDTO couponDTO){
        Coupon coupon = couponMapper.toEntity(couponDTO);
        return service.create(coupon);
    }

    /*@PutMapping("/{id}")
    public Coupon update(@PathVariable Integer id,@Valid  @RequestBody Coupon coupon){
        return service.update(id, coupon);
    }*/

    /*@DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        service.deleteById(id);
        return "Deleted";
    }*/
}
