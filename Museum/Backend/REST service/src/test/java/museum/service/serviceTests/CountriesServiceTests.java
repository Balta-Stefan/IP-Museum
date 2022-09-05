package museum.service.serviceTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import museum.service.models.DTOs.RegionDTO;
import museum.service.services.implementation.CountriesServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.swing.plaf.synth.Region;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@TestPropertySource(locations = "classpath:test.properties")
//@SpringBootTest(classes = CountriesServiceImpl.class)
public class CountriesServiceTests
{
    @Value("${alpha2Region-api-url}")
    private static String alpha2RegionApiURL;

    @Value("${alpha2AndRegion2Cities-api-url}")
    private static String alphaAndRegionApiUrl;

    @Value("${countries-api-url}")
    private static String countriesApiUrl;

    //@Autowired
    private static CountriesServiceImpl countriesService;

    private final MockWebServer mockServer = new MockWebServer();


    @BeforeAll
    public static void beforeall()
    {
        countriesService = new CountriesServiceImpl("a", countriesApiUrl, alpha2RegionApiURL, alphaAndRegionApiUrl);
    }

    @Test
    public void testGetRegionsWithFailedStatusCode()
    {
        String url = mockServer.url("/").url().toString();
        mockServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader("Content-type", "application/json")
                        .setBody("[{\"region\": \"asas\", \"code\": \"12\"}]")
        );


        Mono<RegionDTO[]> regionDtosMono = WebClient.builder()
                .baseUrl(url)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/banana")
                        .build())
                .retrieve()
                .bodyToMono(RegionDTO[].class);

        //assertThrows(WebClientResponseException.class, regionDtosMono::block);
        RegionDTO[] dtos = regionDtosMono.block();


        //RegionDTO[] regions = countriesService.getRegions("FR").block();
        //System.out.println(regions);
    }
}
