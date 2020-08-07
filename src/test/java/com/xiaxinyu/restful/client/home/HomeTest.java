package com.xiaxinyu.restful.client.home;

import com.xiaxinyu.restful.client.exception.RestServiceException;
import com.xiaxinyu.restful.client.spring.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HomeTest {

    @Autowired
    RestClient restClient;

    private String host = "xxxxxx";

    @Test
    public void test_add_visitor() throws RestServiceException {
        String url = String.format("http://%s/manage/v1/iam_visitor_record", host);

        int salt = new Random().nextInt();

        Map<String, Object> params = new HashMap<>();
        params.put("companyName", "xxx" + salt);
        params.put("companySize", "20人及以下");
        params.put("email", "xxx@xxx.com");
        params.put("industry", "xxx" + salt);
        params.put("phone", "12345678996");
        params.put("source", "wechat_xxxloud");
        params.put("userName", "xxx" + salt);
        params.put("versionName", "申请试用免费版");

        String response = restClient.post(url, params, String.class);

        log.info("response={}", response);
    }
}
