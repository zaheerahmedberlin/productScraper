package de.priseGucken.productScraper.controller;

import de.priseGucken.productScraper.entity.AmzProductEntity;
import de.priseGucken.productScraper.entity.Home24Entity;
import de.priseGucken.productScraper.entity.Mirjan24Entity;
import de.priseGucken.productScraper.entity.XXXLutzEntity;
import de.priseGucken.productScraper.model.Mirjan24Model;
import de.priseGucken.productScraper.service.AmzProductService;
import de.priseGucken.productScraper.service.Home24Service;
import de.priseGucken.productScraper.service.Mirjan24Service;
import de.priseGucken.productScraper.service.XXXLutzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ScraperController {

    private Mirjan24Service mirjan24Service;
    private Home24Service home24Service;
    private AmzProductService amzProductService;
    private XXXLutzService xxxLutzService;

    @Autowired
    public ScraperController(Mirjan24Service mirjan24Service, Home24Service home24Service,AmzProductService amzProductService, XXXLutzService xxxLutzService ) {
        this.mirjan24Service = mirjan24Service;
        this.home24Service = home24Service;
        this.amzProductService = amzProductService;
        this.xxxLutzService = xxxLutzService;
    }

    @GetMapping("/test")
    public void test() {
       System.out.println("work test");
    }


    @GetMapping("/getMirjan")
    public List<Mirjan24Entity> getMirjanProducts(@RequestParam String category,String pages) throws IOException {
        return  mirjan24Service.scrapeProducts(category,pages);
    }

    @GetMapping("/home24")
    //option to scrap more pages by https://www.home24.de/sofa-couch/?page=2
    public List<Home24Entity> getProducts(@RequestParam String category,String pages) throws IOException {
        System.out.println("called");
        return home24Service.scrapeProducts(category,pages);
    }

    @GetMapping("amz")
    public List<AmzProductEntity> getAmzProduct(@RequestParam String query) throws IOException {
        return amzProductService.scrapAmzProduct(query);
    }

    @GetMapping("xxxLutz")
    public List<XXXLutzEntity> getXXXLutzProduct(@RequestParam String category, String pages) throws InterruptedException {
        return xxxLutzService.scrapeCategory(category, Integer.parseInt(pages));
    }

}
