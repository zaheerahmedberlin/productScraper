package de.priseGucken.productScraper.service;

import de.priseGucken.productScraper.entity.Mirjan24Entity;
import de.priseGucken.productScraper.repo.Mirjan24Repo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Mirjan24Service {

    private Mirjan24Repo mirjan24Repo;

    @Autowired
    public Mirjan24Service(Mirjan24Repo mirjan24Repo) {
        this.mirjan24Repo = mirjan24Repo;
    }

    public List<Mirjan24Entity> scrapeProducts(String category,String pageNo) throws IOException {
        List<Mirjan24Entity> products = new ArrayList<>();

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.mirjan24.de/"+category+"?page"+pageNo);//374-sofas-couches
        String html = driver.getPageSource();
        Document doc = Jsoup.parse(html);

        //Document doc = connection.get();

        // Check status
//        if (connection.response().statusCode() == 403) {
//            throw new IOException("Access forbidden (403). Try a proxy or slower rate.");
//        }

        Elements productElements = doc.select(".product-miniature");

        for (Element el : productElements) {
            String name = el.select(".product-title a").text();
            String price = el.select(".price").text();
            String productUrl = el.select(".product-title a").attr("href");
            String imageUrl = el.select(".product-thumbnail img").attr("data-full-size-image-url");
            String description = el.select(".productdeliverytabs__time--labeled").text();

            if (imageUrl.isEmpty()) {
                imageUrl = el.select(".product-thumbnail img").attr("src");
            }


            products.add(new Mirjan24Entity(name, description, category, "Merjan24",price,imageUrl,productUrl));
            mirjan24Repo.save(new Mirjan24Entity(name, description, category.replaceAll("[^a-zA-Z]", ""), "Merjan24",price,imageUrl,productUrl));
        }

        return products;
    }
}

