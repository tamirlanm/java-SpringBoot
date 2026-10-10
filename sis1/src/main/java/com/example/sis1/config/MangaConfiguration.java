package com.example.sis1.config;

import com.example.sis1.dto.CreateMangaRequest;
import com.example.sis1.model.MangaStatus;
import com.example.sis1.service.MangaService;
import com.example.sis1.config.MangaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MangaProperties.class)
public class MangaConfiguration {

    private static final Logger log =
            LoggerFactory.getLogger(MangaConfiguration.class);

    @Bean
    public ApplicationRunner logMangaSettings(
            MangaProperties properties
    ) {
        return args -> log.info(
                "Manga settings: seedEnabled={}",
                properties.seedEnabled()
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.sis1", name = "seed-enabled", havingValue = "true"
    )
    public ApplicationRunner seedMangaData(
            MangaService mangaService
    ) {
        return args -> {
            mangaService.create(new CreateMangaRequest(
                    "Demo Manga",
                    "Demo Author",
                    "A sample entry for development.",
                    MangaStatus.ONGOING,
                    10
            ));
            log.info("Demo manga added");
        };
    }
}