package de.priseGucken.productScraper.repo;

import de.priseGucken.productScraper.entity.AmzProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AmzProductRepo extends JpaRepository<AmzProductEntity, Long> {
}
