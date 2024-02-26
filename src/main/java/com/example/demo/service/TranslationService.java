package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {
    private final RestTemplate restTemplate;

    @Autowired
    public TranslationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String translateWord(String word) {
        String apiUrl = "https://api.deeplx.org/translate"; // 替换为实际的API URL
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 创建请求体
        JSONObject requestJson = new JSONObject();
        requestJson.put("text", word);
        requestJson.put("source_lang", "auto");
        requestJson.put("target_lang", "ZH");

        // 封装请求体和请求头到HttpEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);

        // 发送POST请求
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        // 检查响应状态并解析响应
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject responseBody = new JSONObject(response.getBody());

            // 这里假设返回的JSON中有个"data"字段包含翻译的结果
            String translatedText = responseBody.getString("text");

            // 返回翻译后的文本
            return translatedText;
        } else {
            // 处理错误情况，这里只是简单返回一个错误信息
            return "Error: Unable to translate the word.";
        }
        // 构建请求
        // 发送请求并获取响应
        // 解析响应并返回翻译

    }
}
