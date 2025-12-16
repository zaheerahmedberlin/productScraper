package de.priseGucken.productScraper.repo;

import de.priseGucken.productScraper.entity.Home24Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface Home24Repo extends JpaRepository<Home24Entity,Long> {
}
