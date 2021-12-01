package ru.nsu.promocodesharebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "cupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(columnDefinition = "text", nullable = false)
    private String code;

    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING/*, timezone = "Asia/Novosibirsk"*/)
    private Date expirationDate;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Value("false")
    private Boolean isDeleted;

    @Value("false")
    private Boolean isArchive;

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setArchive(Boolean archive) {
        isArchive = archive;
    }

    public String getCode() {
        return code;
    }
}
