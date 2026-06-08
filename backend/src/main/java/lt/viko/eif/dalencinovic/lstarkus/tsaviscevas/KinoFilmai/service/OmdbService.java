package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Servisas, skirtas komunikacijai su OMDb API.
 * Atlieka filmų paiešką ir duomenų gavimą iš išorinės sistemos.
 */
@Service
public class OmdbService {

    private final RestClient restClient;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Value("${omdb.api.url}")
    private String apiUrl;

    public OmdbService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Ieško filmo pagal nurodytą pavadinimą naudodamas OMDb API
     * ir grąžina gautus duomenis.
     *
     * @param title filmo pavadinimas
     * @return OMDb API grąžintas filmo duomenų objektas
     */
    public OmdbMovieResponse searchMovieByTitle(String title) {
        return restClient.get()
                .uri(apiUrl + "?t={title}&apikey={apiKey}", title, apiKey)
                .retrieve()
                .body(OmdbMovieResponse.class);
    }
}