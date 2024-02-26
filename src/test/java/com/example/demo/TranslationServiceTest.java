package com.example.demo;

import com.example.demo.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
public class TranslationServiceTest {

    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    @Autowired
    private TranslationService translationService;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        ReflectionTestUtils.setField(translationService, "restTemplate", restTemplate);
    }

    @Test
    public void testTranslateWord() {
        // 模拟API响应
        mockServer.expect(requestTo("https://api.deeplx.org/translate"))
                .andRespond(withSuccess("{\"data\":\"你好, 世界\"}", MediaType.APPLICATION_JSON));

        // 调用方法进行测试
        String result = translationService.translateWord("Hello, World");

        // 验证返回结果
        assertEquals("你好, 世界", result);

        // 验证所有预期的请求都已经被调用
        mockServer.verify();
    }
}
