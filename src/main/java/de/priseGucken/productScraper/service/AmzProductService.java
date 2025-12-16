package de.priseGucken.productScraper.service;

import de.priseGucken.productScraper.entity.AmzProductEntity;
import de.priseGucken.productScraper.repo.AmzProductRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AmzProductService {

    private AmzProductRepo amzProductRepo;

    @Autowired
    public AmzProductService(AmzProductRepo amzProductRepo) {
        this.amzProductRepo = amzProductRepo;
    }

    private static final String BASE_URL = "https://www.amazon.de/s?k=%s&page=%d";
    private static final int MAX_PAGES = 8;
    private static final int TIMEOUT_MS = 10000;

    public List<AmzProductEntity> scrapAmzProduct(String searchQuery) throws IOException {

        String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
       // String baseUrl = "https://www.amazon.de/s?k=" + encodedQuery;
        List<Map<String, String>> result = new ArrayList<>();
        List<AmzProductEntity> amzProductList = new ArrayList<>();


        for(int page =1; page <=MAX_PAGES;page++) {

            String url = String.format(BASE_URL, encodedQuery, page);
            System.out.println("Scraping page " + page + " -> " + url);


            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/118.0.5993.118 Safari/537.36")
                    .timeout(10_000)
                    .get();

            Elements products = doc.select("div.s-main-slot div.s-result-item[data-asin]");

            for (Element product : products.stream().limit(100).toList()) {
                String asin = product.attr("data-asin");
                String productTitle = product.select(".a-link-normal h2").text();
                String productLink = product.select(".a-link-normal").attr("abs:href");
                String price = product.select("span.a-price span.a-offscreen").text();
                String imageUrl = product.select("img.s-image").attr("src");
                String category = searchQuery;

                // Try to get a small description snippet
                String productDescription = product.select("div.a-row.a-size-base.a-color-secondary, span.a-text-normal")
                        .text();

                if (!asin.isEmpty()) {
                    AmzProductEntity amzProduct = new AmzProductEntity(asin, productTitle, productDescription, category, "amazon", price, imageUrl, productLink);
                    amzProductList.add(amzProduct);
                    System.out.println(asin);
                }

            }
        }
        saveDB(amzProductList);
        return amzProductList;
    }

    private void saveDB(List<AmzProductEntity> amzProductEntities){
        amzProductRepo.saveAllAndFlush(amzProductEntities);
    }
}
