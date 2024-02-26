package com.example.demo;

import com.example.demo.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class TranslationServiceTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    public void testTranslateWord() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        // 模拟API响应
        mockServer.expect(requestTo("https://api.deeplx.org/translate"))
                .andRespond(withSuccess("{\"data\":{\"text\":\"你好, 世界\"}}", MediaType.APPLICATION_JSON));

        // 创建服务实例，并使用带有模拟RestTemplate的构造函数
        TranslationService service = new TranslationService(restTemplateBuilder);

        // 调用方法进行测试
        String result = service.translateWord("Hello, World");

        // 验证返回结果
        assert(result.equals("你好, 世界"));

        // 验证所有预期的请求都已经被调用
        mockServer.verify();
    }
}
