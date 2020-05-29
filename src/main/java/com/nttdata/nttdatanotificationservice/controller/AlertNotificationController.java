package com.nttdata.nttdatanotificationservice.controller;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableConfigurationProperties(NotificationServiceProperties.class)
public class AlertNotificationController {

    @Autowired
    WebHookService webHookService;

    @Autowired
    NotificationServiceProperties notificationServiceProperties;

    Logger logger = LoggerFactory.getLogger(AlertNotificationController.class);

<<<<<<< Updated upstream:src/main/java/com/nttdata/nttdatanotificationservice/controller/AlertNotificationController.java
    @PostMapping(value = "alert/{token}/{routes}", produces = MediaType.APPLICATION_JSON_VALUE)
=======
    private static final TypeAdapter<SplunkAlert> strictSplunkAddapter = new Gson().getAdapter(SplunkAlert.class);

    @PostMapping(value = "splunk/{token}/{routes}", produces = MediaType.APPLICATION_JSON_VALUE)
>>>>>>> Stashed changes:src/main/java/com/nttdata/nttdatanotificationservice/controller/SplunkNotificationController.java
    public ResponseEntity<String> alert(@PathVariable("token") String token,
                                        @PathVariable("routes") String routes,
                                        @RequestBody SplunkAlert splunkAlert) {
        if (!notificationServiceProperties.getTokens().contains(token)) {
            logger.error("Token failed to validate");
            return new ResponseEntity<>("Token validation failed", HttpStatus.UNAUTHORIZED);
        }

        return webHookService.postMessage(splunkAlert, routes);
    }
}
