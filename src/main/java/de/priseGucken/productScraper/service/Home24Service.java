package de.priseGucken.productScraper.service;

import de.priseGucken.productScraper.entity.Home24Entity;
import de.priseGucken.productScraper.repo.Home24Repo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Home24Service {

    @Value("${scraper.home24-base-url}")
    private String BASE_URL;

  //  private static final String BASE_URL = "https://www.home24.de/search?query=";

    private Home24Repo home24Repo;

    @Autowired
    public Home24Service(Home24Repo home24Repo) {
        this.home24Repo = home24Repo;
    }


    public List<Home24Entity> scrapeProducts(String category, String pages) throws IOException {

        int pageMax = Integer.parseInt(pages);

        List<Home24Entity> products = new ArrayList<>();

        for (int page =1;page <= pageMax;page++) {
            System.out.println("Scraping Products from pageNo" + page + " out of " + pageMax );
            Document doc = Jsoup.connect(BASE_URL + category + "&page=" + page)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10000)
                    .get();


            /*+
            * if no result than check the dic class name a[class=emotion-cache-9y706f e1w63c9z1]
            * mostly change e1w63c9z1
            * and by title e8he6f72 */

            Elements productElements = doc.select("a[class=emotion-cache-9y706f e1w63c9z1]");

            System.out.println("worked after productsElements");


            for (Element productElement : productElements) {
                String name = productElement.select("p[class=e8he6f72 emotion-cache-9dfn53 e11edpza0]").text();
                String price = productElement.select("span[class=emotion-cache-ez8ccn e11edpza0]").text();
                String imageUrl = productElement.select("img").attr("src");
                String productUrl = BASE_URL + productElement.select("a[class=emotion-cache-9y706f eff797u1]").attr("href");
                products.add(new Home24Entity(name, "", category, "Home24", price, imageUrl, productUrl));
                System.out.println("productElements " +productElement +" name " +name +"category " +category +" price "+price);
            }
        }

        saveAll(products);
        return products;

    }

    private void saveAll(List<Home24Entity> home24Entities){
        home24Repo.saveAllAndFlush(home24Entities);
    }
}

