package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author QiYongChuan
 * @Version 1.0
 * 2024/3/24
 */



@Service
public class ImgCreateService {


    @Autowired
    private RestTemplate restTemplate;


    private final String apiKey ="Lu42GOnjdo1K3BuU393Vn84u";


    private final String secretKey = "HZGXSB3M7QRRyuzGl22qNEyZqJkZu7hM";

    private String getAccessToken() {
        String urlTemplate = "https://aip.baidubce.com/oauth/2.0/token?" +
                "grant_type=client_credentials&client_id={apiKey}&client_secret={secretKey}";

        // 使用RestTemplate的getForObject方法直接将参数替换到URL模板中，并执行GET请求
        Map<String, String> vars = new HashMap<>();
        vars.put("apiKey", apiKey);
        vars.put("secretKey", secretKey);

        ResponseEntity<HashMap> response = restTemplate.getForEntity(urlTemplate, HashMap.class, vars);

        return (String) response.getBody().get("access_token");
    }


    public  String generateImageFromText(String word) {
        String accessToken = getAccessToken();

        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> map = new HashMap<>();
        map.put("prompt", word);
        map.put("negative_prompt", "white");
        map.put("size", "1024x1024");
        map.put("steps", 20);
        map.put("n", 1);
        map.put("sampler_index", "DPM++ SDE Karras");

        HttpEntity<HashMap<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<HashMap> response = restTemplate.postForEntity(url, entity, HashMap.class);

        HashMap responseMap = response.getBody();
//        System.out.println(responseMap);

        List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) responseMap.get("data");
        String base64Image = dataList.get(0).get("b64_image").toString();
        // 根据需要进一步处理响应
        System.out.println("base64Image: " + base64Image);
        return base64Image;
    }
}

