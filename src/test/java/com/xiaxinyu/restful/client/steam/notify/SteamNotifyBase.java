package com.xiaxinyu.restful.client.steam.notify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

public class SteamNotifyBase {
    @Value("${restful.steam-notify.route}")
    public String host;

    @Value("${restful.steam-notify.token}")
    public String token;

    public HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", token));
        return headers;
    }
}
