package de.priseGucken.productScraper.repo;

import de.priseGucken.productScraper.entity.XXXLutzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface XXXLutzRepo extends JpaRepository<XXXLutzEntity,Long> {
}
