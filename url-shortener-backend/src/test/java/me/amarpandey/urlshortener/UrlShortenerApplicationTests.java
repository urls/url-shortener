package me.amarpandey.urlshortener;

import me.amarpandey.urlshortener.models.Url;
import me.amarpandey.urlshortener.repository.UrlShortenerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApplicationTests {

    private final String GET_URL_SHORTEN_ENDPOINT = "/";
    private final String POST_URL_SHORTEN_ENDPOINT = "/shorten";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @BeforeEach
    void setup() {
        urlShortenerRepository.deleteAll();
    }

    @Test
    void shouldReturnPaginatedOptionWhenFiltersAreSpecified() throws Exception {
        // when
        var url = "https://facobook.com/";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(POST_URL_SHORTEN_ENDPOINT)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(url))
                .andExpect(status().isOk())
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        JSONObject shortenResponse = new JSONObject(responseAsString).getJSONObject("shortenResponse");

        // then
        Assertions.assertEquals(url, shortenResponse.get("url"));
        Assertions.assertNotNull(shortenResponse.get("shortenCode"));
    }
}