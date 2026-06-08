package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestClientConfigTest {

    @Test
    void restClientCreatesClient() {
        assertNotNull(new RestClientConfig().restClient());
    }
}
