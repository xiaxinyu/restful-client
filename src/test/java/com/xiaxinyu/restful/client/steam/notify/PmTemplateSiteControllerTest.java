package com.xiaxinyu.restful.client.steam.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaxinyu.restful.client.exception.RestServiceException;
import com.xiaxinyu.restful.client.spring.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PmTemplateSiteControllerTest extends SteamNotifyBase{

    @Autowired
    RestClient restClient;

    private JSONObject getPmTemplate(Long templateId) throws RestServiceException {
        String url = String.format("%s%s", host, "/v1/notices/letters/templates/" + templateId);
        log.info("url={}", url);

        String response = restClient.get(url, getHttpHeaders(), String.class);
        log.info("response={}", response);

        return JSONObject.parseObject(response);
    }

    @Test
    public void test_query_pm_template_by_id() throws RestServiceException {
        JSONObject jsonObject = getPmTemplate(31L);
        Assert.assertNotNull(jsonObject);

        String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        log.info(pretty);
    }

    @Test
    public void test_update_pm_template() throws Exception {
        //请求参数
        JSONObject jsonObject = getPmTemplate(31L);
        Assert.assertNotNull(jsonObject);

        JSONObject data = jsonObject.getJSONObject("data");
        Assert.assertNotNull(data);

        String code = data.getString("code");
        data.put("code", code + "-" + new Random().nextInt());

        //获取参数
        Map<String, Object> params = data.getInnerMap();

        //发送请求
        Long templateId = 1L;
        String url = String.format(host + "/v1/notices/letters/templates/%d", templateId);
        log.info("请求URL：url={}", url);
        String response = restClient.put(url, getHttpHeaders(), params, String.class);
        log.info("response={}", response);

        String pretty = JSON.toJSONString(JSONObject.parseObject(response), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        log.info(pretty);
    }
}
