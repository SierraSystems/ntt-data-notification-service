package com.nttdata.nttdatanotificationservice.service;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebHookServiceImpl implements WebHookService {
    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);

    @Autowired
    ChannelServiceFactory channelServiceFactory;

    public ResponseEntity<String> postMessage(Notification notification, WebHookParams webHookParams) {

        webHookParams.getWebHookUrls().stream().forEach(webHookUrl -> {
            ChatApp chatApp = webHookUrl.getChatApp();

            Optional<ChannelService> channelService = channelServiceFactory.getChanelService(chatApp);
            logger.info("Posting to {}", chatApp);

            channelService.ifPresent(service -> post(webHookUrl.getUrl(), service.generatePayload(
                notification, webHookUrl.getUrl())));
        });

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    private void post(String url, Object postObj) {
        try {
            Gson postJson = new Gson();

            logger.info("{}", postJson.toJson(postObj, Object.class));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(postJson.toJson(postObj, Object.class), headers);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, request, String.class);
            logger.info("Success: {}", response);
        } catch (Exception e) {
            logger.error("Exception in post", e);
        }
    }
}
