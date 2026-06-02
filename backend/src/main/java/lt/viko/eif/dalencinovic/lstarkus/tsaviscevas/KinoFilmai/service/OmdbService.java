package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.service;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.dto.OmdbMovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

    public OmdbMovieResponse searchMovieByTitle(String title) {
        return restClient.get()
                .uri(apiUrl + "?t={title}&apikey={apiKey}", title, apiKey)
                .retrieve()
                .body(OmdbMovieResponse.class);
    }
}