package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;
import com.nttdata.nttdatanotificationservice.sources.WebHookParams;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebHookServiceImpl implements WebHookService {
    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);

    @Autowired
    ChannelServiceFactory channelServiceFactory;

    public ResponseEntity<String> postMessage(GenericAlert genericAlert, String routes) {
        Gson gson = new Gson();
        byte[] decodedRoutesBytes = Base64.getUrlDecoder().decode(routes);
        String decodedRoutesUrl = new String(decodedRoutesBytes);

        logger.info(decodedRoutesUrl);

        WebHookParams webHookParams = gson.fromJson(decodedRoutesUrl, WebHookParams.class);

        logger.info(webHookParams.getWebHookUrls().toString());

        webHookParams.getWebHookUrls().stream().forEach(webHookUrl -> {
            ChatApp chatApp = webHookUrl.getChatApp();

            Optional<ChannelService> channelService = channelServiceFactory.getChanelService(chatApp);
            logger.info("Posting to {}", chatApp);

            channelService.ifPresent(service -> post(webHookUrl.getUrl(), service.generatePayload(genericAlert)));
        });

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    private void post(String url, Object postObj) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url, postObj, String.class);
            logger.info("Success");
        } catch (Exception e) {
            logger.error("Exception in post", e);
        }
    }
}
