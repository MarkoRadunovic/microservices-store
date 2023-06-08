package com.practices;

import com.practices.dto.ArticleRequest;
import com.practices.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ArticleServiceApplicationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest").withNetworkMode("host");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleRepository articleRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongo.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateArticle() throws Exception {
        ArticleRequest articleRequest = getArticleRequest();
        String articleRequestString = objectMapper.writeValueAsString(articleRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, articleRepository.findAll().size());
    }

    private ArticleRequest getArticleRequest() {
        return ArticleRequest.builder()
                .name("Iphone13")
                .description("Iphone 13 black with 256GB memory")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
}