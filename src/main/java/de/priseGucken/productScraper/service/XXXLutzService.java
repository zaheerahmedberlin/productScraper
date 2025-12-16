package de.priseGucken.productScraper.service;

import de.priseGucken.productScraper.entity.XXXLutzEntity;
import de.priseGucken.productScraper.repo.XXXLutzRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Service
public class XXXLutzService {

  //  private final Logger log = LoggerFactory.getLogger(XXXLutzService.class);
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private XXXLutzRepo xxxLutzRepo;

    @Autowired
    public XXXLutzService(XXXLutzRepo xxxLutzRepo) {
        this.xxxLutzRepo = xxxLutzRepo;
    }

    @Value("${scraper.xxxLutz-base-url}")
    private String baseUrl;

    @Value("${scraper.delay-ms}")
    private long delayMs;


    public List<XXXLutzEntity> scrapeCategory(String categorySlug, int maxPages) throws InterruptedException {
        List<XXXLutzEntity> results = new ArrayList<>();


        for (int page = 1; page <= maxPages; page++) {
            String pageUrl = buildCategoryPageUrl(categorySlug, page);
            System.out.println("Fetching category page: {} " + pageUrl);

             Thread.sleep(delayMs);

            try {
                WebDriver driver = new ChromeDriver();
                driver.get(pageUrl);
                String html = driver.getPageSource();
                Document doc = Jsoup.parse(html);


// NOTE: the CSS selectors below are generic placeholders; inspect xxxlutz.de and update them.
                Elements productCards = doc.select("div[class=_kYCunYMuX5GLTYBS]");
                if (productCards.isEmpty()) {
// Try another common selector
                    productCards = doc.select(".product, .product-list-item");
                }
                if (productCards.isEmpty()) {
                }

                for (Element card : productCards) {
// Extract fields defensively
                    String title = firstText(card, "h2, h3, .title, ._OdXj59QKfUQdrect");
                    String link = firstAttr(card, "a[href]", "href");
                    if (link != null && !link.startsWith("http")) link = baseUrl + link;
                    String image = firstAttr(card, "img[src]", "src");
                    String price = firstText(card, ".___e_bQDvCH2zM3Up");
                    String id = firstAttr(card, "a[data-product-id]", "data-product-id");
                    if (id == null || id.isBlank()) id = link != null ? link : Integer.toString(card.hashCode());

//(String title, String description, String category, String vendor, String price, String image, String url)


                    System.out.println("Link " + link);
                    XXXLutzEntity dto = new XXXLutzEntity(title, "", categorySlug, "XXXLutz", price, image, link);
                    results.add(dto);
                }

            } catch (Exception e) {

                break;
            }
        }
        saveDB(results);
        return results;

    }

    private String buildCategoryPageUrl(String categorySlug, int page) {
        if (page <= 1) return baseUrl + categorySlug;
        return baseUrl + categorySlug + "&page=" + page;
    }

    private String firstText(Element el, String cssQuery) {
        Element found = el.selectFirst(cssQuery);
        return found != null ? found.text().trim() : "";
    }

    private String firstAttr(Element el, String cssQuery, String attr) {
        Element found = el.selectFirst(cssQuery);
        return found != null ? found.absUrl(attr).isEmpty() ? found.attr(attr) : found.absUrl(attr) : null;
    }

    private void saveDB(List<XXXLutzEntity> xxxLutzEntities){
        xxxLutzRepo.saveAllAndFlush(xxxLutzEntities);
    }
}
