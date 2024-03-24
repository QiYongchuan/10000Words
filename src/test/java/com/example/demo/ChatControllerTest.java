package com.example.demo;

/**
 * @author QiYongChuan
 * @Version 1.0
 * 2024/3/23
 */
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testChat() {
        String question = "你好"; // 这里替换成您想要测试的问题
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/chat", question, String.class);

        assertEquals(200, response.getStatusCodeValue());
        // 这里根据实际返回值进行断言
    }
}

