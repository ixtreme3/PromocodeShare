package ru.nsu.promocodesharebackend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CouponDTO {

    //private Long id;

    @NotNull(message = "Please enter the coupon shop id")
    private Long shopId;

    @NotBlank(message = "Please enter the coupon code")
    private String code;

    @NotBlank(message = "Please enter the coupon name")
    private String name;

    @NotBlank(message = "Please enter the coupon description")
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING/*, timezone = "Asia/Novosibirsk"*/)
    @NotNull(message = "Please enter the coupon expiration date")
    private Date expirationDate;

    private Long userId;

    private Boolean isDeleted;

    private Boolean isArchive;
}

