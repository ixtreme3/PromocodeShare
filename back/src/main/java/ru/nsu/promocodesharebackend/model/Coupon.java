package ru.nsu.promocodesharebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "shop_id")
    @JsonIgnoreProperties(value = {"id", "title", "href", "category"})
//    @NotNull(message = "Please enter the coupon shop id")
    private Shop shop;

    @Column(columnDefinition = "text", nullable = false)
//    @NotBlank(message = "Please enter the coupon code")
    private String code;

    @Column(columnDefinition = "text", nullable = false)
//    @NotBlank(message = "Please enter the coupon name")
    private String name;

    @Column(columnDefinition = "text", nullable = false)
//    @NotBlank(message = "Please enter the coupon description")
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING/*, timezone = "Asia/Novosibirsk"*/)
//    @NotNull(message = "Please enter the coupon expiration date")
    private Date expirationDate;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"id", "vkLink"})
    private User user;

    @Value("false")
    @JsonIgnore
    private Boolean isDeleted;

    @Value("false")
    @JsonIgnore
    private Boolean isArchive;
}
