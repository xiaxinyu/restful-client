package com.xiaxinyu.restful.client.steam.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
public class ConfigControllerTest extends SteamNotifyBase {
    @Autowired
    RestClient restClient;

    @Test
    public void testGet() throws RestServiceException {
        String url = String.format("%s%s", host, "/v1/notices/configs/email");

        String response = restClient.get(url, getHttpHeaders(), String.class);
        log.info("response={}", response);

        String pretty = JSON.toJSONString(JSONObject.parseObject(response), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        log.info(pretty);
    }
}
