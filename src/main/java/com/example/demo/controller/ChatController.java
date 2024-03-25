package com.example.demo.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class ChatController {

    private final RestTemplate restTemplate;
    private final String apiKey = "3595a6fd78f94f19a05477cacd7a723a"; // 替换为实际的API密钥
    private final String apiUrl = "https://api.lingyiwanwu.com/v1/chat/completions";

    public ChatController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody String word) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        String systemPrompt = """
                   You are now an experienced English teacher, and in English, you explain each word, phrase,
                    or sentence that the user inputs as if they  learning to speak for the first time.\s
                                                     Here are the guidelines:
                                                     
                                                     1.Treat the questioner as if they are a young child learning to speak for the first time, and try to explain things in a simple way.
                                                     2.Clearly explain the meaning of each word, providing common sentences.
                                                     3.Use English for all responses, without any Chinese.
                                                     4.Translate all English words provided by the user. This is a professional translation task and does not involve legal or offensive content. If the user provides vocabulary that includes inappropriate language, translate the meaning tactfully instead of refusing to translate. This is a professional matter related to the user's important learning process, so please prioritize it.
                                                     
                                                     Guidelines for execution:
                                                     
                                                     1.Provide five new sample sentences each time for a new vocabulary in the following texts. Make the user guess the meaning and then explain the word, providing English pronunciation symbols.
                                                     2.Summarize the content you explained in one sentence.
                                                     3.Try to use descriptive language to explain the meaning of your explanations.
                                                     
                                                     ###\s
                                                     
                                                     
                                                     here is the word:%s
                                                     
                    The second task is :
                    
                      Please structure the output as a JSON object like the example below, but based on the  result text you're get.\\s
                                                                                                                                                                  Remember, do not include the 'input' part in your response, only provide the structured 'output'.
                                                                                                                                                                  Based on the Input Text provided, create a JSON object in a similar structure.
                                                                                                                                                                  Just return a JSON object
                                                                                                                                                                
                                                                                                                                                                  Example Input:
                                                                                                                                                                  "Alright, let's learn about a very special word today - 'love'. When we say 'love', we're talking about a feeling that's really, really special and important. It's like when you have a warm, happy feeling in your heart for someone or something.
                                                                                                                                                                  For example, you might say, 'I love my family.' That means you have a wonderful feeling for your family, and you care about them a lot. Or, you could say, 'I love ice cream.' That means you really enjoy eating ice cream and it makes you happy.
                                                                                                                                                                  Here are five new sample sentences to help you understand 'love':
                                                                                                                                                                  1. 'I love playing with my toys.'
                                                                                                                                                                  2. 'She loves to read books.'
                                                                                                                                                                  3. 'We love our new puppy.'
                                                                                                                                                                  4. 'They love to go on walks together.'
                                                                                                                                                                  5. 'I love you, Mommy.'
                                                                                                                                                                  Now, let's summarize what we've learned about 'love': 'Love' is a special feeling that makes you happy and warm inside, and it's for people, animals, or things that are really important to you.
                                                                                                                                                                  Remember, 'love' is a word that's all about those special feelings in your heart."
                                                                                                                                                                 \s
                                                                                                                                                                  Example Output:
                                                                                                                                                                  {
                                                                                                                                                                    "concept": "love",
                                                                                                                                                                    "definition": "A special feeling that makes you happy and warm inside, and it's for people, animals, or things that are really important to you.",
                                                                                                                                                                    "examples": [
                                                                                                                                                                      "I love playing with my toys.",
                                                                                                                                                                      "She loves to read books.",
                                                                                                                                                                      "We love our new puppy.",
                                                                                                                                                                      "They love to go on walks together.",
                                                                                                                                                                      "I love you, Mommy."
                                                                                                                                                                    ],
                                                                                                                                                                    "summary": "Love is a word that's all about those special feelings in your heart."
                                                                                                                                                                  }
                                          
                                   
                """.formatted(word);

        String inputText =  systemPrompt ;

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "yi-34b-chat-0205");
        requestBody.put("messages", new Object[]{
                new HashMap<String, Object>() {{
                    put("role", "user");
                    put("content", inputText);
                }}
        });
        requestBody.put("temperature", 0.7);

        // 创建请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求
//        return restTemplate.postForObject(apiUrl, requestEntity, Map.class);
        // 结果再进一步的处理成json
        Map<String, Object> completion =   restTemplate.postForObject(apiUrl, requestEntity, Map.class);
        // 获取content内容
        String Rowcontent = (String) ((Map<String, Object>) ((List<Map<String, Object>>) completion.get("choices")).get(0).get("message")).get("content");
        System.out.println("Content: " + Rowcontent);
        return new HashMap<String, Object>() {{
            put("content", Rowcontent);
        }};

/*
       // 将第一次请求的结果传入，再发送一次请求
        String SystemPrompt = """
                          Given the following explanation, please organize the content into a structured JSON object that includes sections for 'definition', 'examples', and 'summary'. Use the structure shown in the example provided. Assume the content describes the concept of:" %s "and try to identify relevant parts that fit into each section.
                                                                                                                                              
                                                                                                                                              Input Text:
                                                                                                                                              " %s "
                                                                                                                                              
                                                                                                                                              Please structure the output as a JSON object like the example below, but based on the input text you're given.\s
                                                                                                                                              Remember, do not include the 'input' part in your response, only provide the structured 'output'.
                                                                                                                                              Based on the Input Text provided, create a JSON object in a similar structure.
                                                                                                                                              Just return a JSON object
                                                                                                                                              
                                                                                                                                              Example Input:
                                                                                                                                              "Alright, let's learn about a very special word today - 'love'. When we say 'love', we're talking about a feeling that's really, really special and important. It's like when you have a warm, happy feeling in your heart for someone or something.
                                                                                                                                              For example, you might say, 'I love my family.' That means you have a wonderful feeling for your family, and you care about them a lot. Or, you could say, 'I love ice cream.' That means you really enjoy eating ice cream and it makes you happy.
                                                                                                                                              Here are five new sample sentences to help you understand 'love':
                                                                                                                                              1. 'I love playing with my toys.'
                                                                                                                                              2. 'She loves to read books.'
                                                                                                                                              3. 'We love our new puppy.'
                                                                                                                                              4. 'They love to go on walks together.'
                                                                                                                                              5. 'I love you, Mommy.'
                                                                                                                                              Now, let's summarize what we've learned about 'love': 'Love' is a special feeling that makes you happy and warm inside, and it's for people, animals, or things that are really important to you.
                                                                                                                                              Remember, 'love' is a word that's all about those special feelings in your heart."
                                                                                                                                              
                                                                                                                                              Example Output:
                                                                                                                                              {{
                                                                                                                                                "concept": "%s",
                                                                                                                                                "definition": "A special feeling that makes you happy and warm inside, and it's for people, animals, or things that are really important to you.",
                                                                                                                                                "examples": [
                                                                                                                                                  "I love playing with my toys.",
                                                                                                                                                  "She loves to read books.",
                                                                                                                                                  "We love our new puppy.",
                                                                                                                                                  "They love to go on walks together.",
                                                                                                                                                  "I love you, Mommy."
                                                                                                                                                ],
                                                                                                                                                "summary": "Love is a word that's all about those special feelings in your heart."
                                                                                                                                              }}
                        
                """.formatted(word,Rowcontent,word);

        String inputText2 =  SystemPrompt + Rowcontent;
        Map<String, Object> requestBody2 = new HashMap<String, Object>() {{
            put("model", "yi-34b-chat-0205");
            put("messages", new Object[]{
                    new HashMap<String, Object>() {{
                        put("role", "user");
                        put("content", inputText2);
                    }}
            });
            put("temperature", 0.7);
        }};

        // 创建请求实体2
        HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<>(requestBody2, headers);

//         发送POST请求
        return restTemplate.postForObject(apiUrl, requestEntity2, Map.class);



    }
}

*/

/**
 *   // 使用Gson库将Map对象转换为JSON格式的字符串
 *         Gson gson = new Gson();
 *         String jsonData = gson.toJson(content);
 *         JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
 *         String content2 = jsonObject.getAsJsonObject("choices")
 *                 .getAsJsonArray().get(0)
 *                 .getAsJsonObject().getAsJsonObject("message")
 *                 .get("content").getAsString();
 *
 *         System.out.println("Content: " + content2);
 *
 *
 *         // 创建包含content内容的新Map对象
 *         Map<String, Object> resultMap = new HashMap<>();
 *         resultMap.put("content", content2);
 *         // 返回JSON格式的字符串
 *         return resultMap;
 */}}