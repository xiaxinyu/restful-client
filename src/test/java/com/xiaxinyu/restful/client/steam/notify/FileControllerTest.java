package com.xiaxinyu.restful.client.steam.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaxinyu.restful.client.common.JSONUtils;
import com.xiaxinyu.restful.client.exception.RestServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FileControllerTest extends SteamNotifyBase {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test_files_upload() throws RestServiceException {

        // 文件必须封装成FileSystemResource这个类型后端才能收到附件
        String filePath = "C:/通知流程图.jpg";
        //File file = new File(filePath);
        FileSystemResource resource = new FileSystemResource(filePath);

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", String.format("bearer %s", token));

        // 然后所有参数要封装到MultiValueMap里面
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", resource);

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(form, headers);

        // 调用接口即可
        String url = String.format("%s%s", host, "/v1/notices/files/single");
        String response = restTemplate.postForObject(url, param, String.class);
        log.info("response={}", response);

        JSONObject jsonObject = JSONObject.parseObject(response);
        Assert.assertNotNull(jsonObject);

        String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        log.info(pretty);

        //验证结果
        Assert.assertTrue(JSONUtils.isSuccess(jsonObject));
    }
}
