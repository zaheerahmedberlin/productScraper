package de.priseGucken.productScraper.entity;

import jakarta.persistence.*;

import java.util.Base64;
import java.util.Objects;

@Entity
public class AmzProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asin;
    private String title;
    @Column(length = 2048)
    private String description;
    private String category;
    private String vendor;
    private String price;
    @Column(length = 2048)
    private String image;
    @Column(length = 2048)
    private String url;


    public AmzProductEntity(String asin, String title, String description, String category, String vendor, String price, String image, String url) {
        this.asin = asin;
        this.title = title;
        this.description = Base64.getEncoder().encodeToString(description.getBytes());
        this.category = category;
        this.vendor = vendor;
        this.price = price;
        this.image = Base64.getEncoder().encodeToString(image.getBytes());
        this.url =Base64.getEncoder().encodeToString(url.getBytes());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmzProductEntity)) return false;
        AmzProductEntity that = (AmzProductEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAsin(), that.getAsin()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getVendor(), that.getVendor()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getImage(), that.getImage()) && Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAsin(), getTitle(), getDescription(), getCategory(), getVendor(), getPrice(), getImage(), getUrl());
    }
}
