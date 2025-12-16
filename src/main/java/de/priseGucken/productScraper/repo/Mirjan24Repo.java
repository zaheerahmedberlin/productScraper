package de.priseGucken.productScraper.repo;

import de.priseGucken.productScraper.entity.Mirjan24Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface Mirjan24Repo extends JpaRepository<Mirjan24Entity, Long> {
}
