package me.amarpandey.urlshortener;

import me.amarpandey.urlshortener.repository.UrlShortenerRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static me.amarpandey.urlshortener.utils.Constants.INVALID_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApplicationTests {

    private final String GET_URL_SHORTEN_ENDPOINT = "/{SHORTEN_CODE}";

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @BeforeEach
    void setup() {
        urlShortenerRepository.deleteAll();
    }

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldReturnShortenCodeForNewUrl() throws Exception {
        // when
        var url = "https://github.com/";
        String responseAsString = generateShortenCodeForGivenUrl(url, status().isOk());
        JSONObject shortenResponse = new JSONObject(responseAsString);

        // then
        Assertions.assertEquals(url, shortenResponse.get("url"));
        Assertions.assertNotNull(shortenResponse.get("shortenCode"));
    }


    @Test
    void shouldReturnSameShortenCodeForOldURLs() throws Exception {
        // when
        var url = "https://amarpandey.com/";
        String responseAsString1 = generateShortenCodeForGivenUrl(url, status().isOk());
        JSONObject shortenResponse1 = new JSONObject(responseAsString1);

        // then
        Assertions.assertEquals(url, shortenResponse1.get("url"));
        Assertions.assertNotNull(shortenResponse1.get("shortenCode"));

        // when
        String responseAsString2 = generateShortenCodeForGivenUrl(url, status().isOk());
        JSONObject shortenResponse2 = new JSONObject(responseAsString2);

        // then
        Assertions.assertEquals(shortenResponse1.get("url"), shortenResponse2.get("url"));
    }


    @Test
    void shouldReturnErrorResponseForWrongURLs() throws Exception {
        // when
        var url = "ThisIsNotAURL";
        String responseAsString = generateShortenCodeForGivenUrl(url, status().isBadRequest());
        String shortenResponse = new JSONObject(responseAsString).getString("message");

        // then
        Assertions.assertEquals(INVALID_URL, shortenResponse);
    }


    @Test
    void shouldRedirectToLongURLForValidShortenCode() throws Exception {

        // given
        String responseAsString = generateShortenCodeForGivenUrl("http://mongodb.com", status().isOk());
        JSONObject shortenResponse = new JSONObject(responseAsString);

        mvc.perform(get(GET_URL_SHORTEN_ENDPOINT, shortenResponse.get("shortenCode")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl((String) shortenResponse.get("url")))
                .andReturn();


    }

    @Test
    void shouldRedirectToRootURLForInvalidShortenCode() throws Exception {
        mvc.perform(get(GET_URL_SHORTEN_ENDPOINT, "SomeRandomCode"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }


    private String generateShortenCodeForGivenUrl(String url, ResultMatcher matcher) throws Exception {
        String POST_URL_SHORTEN_ENDPOINT = "/shorten";
        MvcResult mvcResult = mvc.perform(post(POST_URL_SHORTEN_ENDPOINT)
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(url))
                .andExpect(matcher)
                .andReturn();

        return mvcResult.getResponse().getContentAsString();
    }
}