package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Konfigūracijos klasė, atsakinga už HTTP kliento nustatymus
 * ir ryšį su išorinėmis paslaugomis.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}