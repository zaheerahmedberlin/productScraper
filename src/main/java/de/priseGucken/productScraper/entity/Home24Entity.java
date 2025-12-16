package de.priseGucken.productScraper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Home24Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String category;
    private String vendor;
    private String price;
    private String image;
    private String url;

    public Home24Entity(String title, String description, String category, String vendor, String price, String image, String url) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.vendor = vendor;
        this.price = price;
        this.image = image;
        this.url = url;
    }
}
