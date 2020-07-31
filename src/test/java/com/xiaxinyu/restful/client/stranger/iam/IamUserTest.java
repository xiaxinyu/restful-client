package com.xiaxinyu.restful.client.stranger.iam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaxinyu.restful.client.exception.RestServiceException;
import com.xiaxinyu.restful.client.spring.RestClient;
import com.xiaxinyu.restful.client.stranger.notify.SteamNotifyBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IamUserTest {

    @Autowired
    RestClient restClient;

    private String host;
    private Integer orgId;
    private String token;
    private Integer projectId;

    @Before
    public void before() {
        host = "http://xxxxxx";
        orgId = 1;
        token = "c56980b5-57f0-46b0-95b7-4da005b5b20d";
        projectId = 1381;
    }

    @Test
    public void test_create_user() throws RestServiceException {
        //请求地址
        //http://xxxxxx/steamiam/v1/organizations/1/user
        String url = host + "/steamiam/v1/organizations/" + orgId + "/user";
        log.info("Request Url={}", url);

        //权限认证
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", token));

        //参数
        //{"email":"test@qq.com","loginName":"test","realName":"test","password":"Shangh2468~","roleIds":[2],"repwd":"Shangh2468~"}
        int salt = new Random().nextInt();
        Map<String, Object> params = new HashMap<>();
        params.put("loginName", "test-" + salt);
        params.put("realName", "test-" + salt);
        params.put("password", "Shangh2468~");
        params.put("repwd", "Shangh2468~");
        params.put("email", "test" + salt + "@qq.com");
        params.put("roleIds", Arrays.asList(2)); //1: 组织成员；2: 组织管理员

        //打印请求参数
        String ree_pretty = JSON.toJSONString(params, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        log.info(ree_pretty);

        //请求
        String response = restClient.post(url, headers, params, String.class);
        Assert.assertNotNull(response);

        //处理报文
        JSON responseObj = JSONObject.parseObject(response);
        String resp_pretty = JSON.toJSONString(responseObj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        log.info(resp_pretty); // 20000：成功；30000：失败和失败信息
    }

    @Test
    public void test_grant_organizaiton_user_To_project() throws RestServiceException {
        //请求地址
        // http://xxxxxx/steamiam/v1/projects/1381/iam_user/bind/users
        String url = host + "/steamiam/v1/projects/" + projectId + "/iam_user/bind/users";
        log.info("Request Url={}", url);

        //权限认证
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", token));

        //参数
        //{"userIds":[14989],"roleIds":[6]}
        int salt = new Random().nextInt();
        Map<String, Object> params = new HashMap<>();
        params.put("userIds", Arrays.asList(14989)); //从test_create_user中返回报文中有userId
        params.put("roleIds", Arrays.asList(6)); //5: 项目成员；6: 项目管理员

        //打印请求参数
        String ree_pretty = JSON.toJSONString(params, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        log.info(ree_pretty);

        //请求
        String response = restClient.post(url, headers, params, String.class);
        log.info("response={}", response);

        //处理报文
        JSON responseObj = JSONObject.parseObject(response);
        String resp_pretty = JSON.toJSONString(responseObj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        log.info(resp_pretty); // 20000：成功；30000：失败和失败信息
    }
}
