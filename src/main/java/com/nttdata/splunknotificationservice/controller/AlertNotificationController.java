package com.nttdata.splunknotificationservice.controller;

import com.nttdata.splunknotificationservice.configuration.SplunkProperites;
import com.nttdata.splunknotificationservice.splunk.models.SplunkAlert;
import com.nttdata.splunknotificationservice.service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableConfigurationProperties(SplunkProperites.class)
public class AlertNotificationController {

    @Autowired
    WebHookService webHookService;

    @Autowired
    SplunkProperites splunkProperites;

    Logger logger = LoggerFactory.getLogger(AlertNotificationController.class);

    @PostMapping(value = "alert/{token}/{routes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alert(@PathVariable("token") String token,
                                        @PathVariable("routes") String routes,
                                        @RequestBody SplunkAlert splunkAlert) {
        if (!splunkProperites.getTokens().contains(token)) {
            logger.error("Token failed to validate");
            return new ResponseEntity<>("Token validation failed", HttpStatus.UNAUTHORIZED);
        }
        return webHookService.postMessage(splunkAlert, routes);
    }
}
