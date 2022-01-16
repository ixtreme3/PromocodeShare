package ru.nsu.promocodesharebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String name; //(название магазина, по возможности на русском)

    @Column(columnDefinition = "text", nullable = true) //nullable = true, т.к. парсим в два прохода
    private String title; //(еще одно название магазина, используется в API Пикабу,
                            // возможно, в дальнейшем понадобится, пока сохраним)

    @Column(columnDefinition = "text", nullable = false)
    private String href; //(ссылка в Пикабу)

    @Column(columnDefinition = "text", nullable = false)
    private String category; //(категория магазина)

    @Column(columnDefinition = "text")
    private String imageURL; // URL картинки магазина
}
