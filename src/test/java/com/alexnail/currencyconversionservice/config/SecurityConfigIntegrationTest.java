package com.alexnail.currencyconversionservice.config;

import com.alexnail.currencyconversionservice.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class SecurityConfigIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate("user", "password");
    private URL base;

    @Test
    @DisplayName("Home resource is available for all users")
    void whenUserRequestsHomePage_ThenNoAuthenticationRequired() throws MalformedURLException {
        restTemplate = new TestRestTemplate();
        base = new URL("http://localhost:" + port);

        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Currency conversion service"));
    }

    @Test
    @DisplayName("API resource is available for an authorized user")
    void whenUserRequestsApiResourceWithCorrectCredentials_thenSuccess() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL("http://localhost:" + port + "/api/wallets");

        ResponseEntity<Wallet[]> response =  restTemplate.getForEntity(base.toString(), Wallet[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("API resource is unavailable for an unauthorized user")
    void whenUserRequestsApiResourceWithWrongCredentials_thenUnauthorizedPage() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        base = new URL("http://localhost:" + port + "/api/wallets");

        ResponseEntity<Wallet[]> response = restTemplate.getForEntity(base.toString(), Wallet[].class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("Swagger UI is unavailable for an unauthorized user")
    void whenUnauthorizedUserRequestsSwaggerUi_thenUnauthorizedPage() throws MalformedURLException {
        restTemplate = new TestRestTemplate();
        base = new URL("http://localhost:" + port + "/swagger-ui.html");

        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}