package com.xiaxinyu.restful.client.open.api;

import com.xiaxinyu.restful.client.exception.RestServiceException;
import com.xiaxinyu.restful.client.spring.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OpenAPIOAuthTest {

    @Autowired
    RestClient restClient;

    @Test
    public void testGetToken() throws RestServiceException {
        String url = String.format("http://api.steamuat.crcloud.com/oauth/oauth/token?grant_type=%s&client_id=%s&client_secret=%s", "client_credentials", "xman", "1127KoKo");

        String response = restClient.post(url, null, String.class);
        log.info("response={}", response);
    }
}
