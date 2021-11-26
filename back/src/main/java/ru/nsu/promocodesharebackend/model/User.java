package ru.nsu.promocodesharebackend.model;

import javax.persistence.*;

@Entity
@Table(name="\"user\"")
public class User {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vk_link")
    private String vkLink;

    public User(Long id, String name, String vkLink) {
        this.id = id;
        this.name = name;
        this.vkLink = vkLink;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vkLink='" + vkLink + '\'' +
                '}';
    }
}
