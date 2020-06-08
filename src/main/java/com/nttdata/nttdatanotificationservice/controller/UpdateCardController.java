package com.nttdata.nttdatanotificationservice.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nttdata.nttdatanotificationservice.configuration.NotificationBody;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import com.nttdata.nttdatanotificationservice.teams.TeamsChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UpdateCardController {
  Logger logger = LoggerFactory.getLogger(UpdateCardController.class);

  @Autowired
  NotificationServiceProperties notificationServiceProperties;

  @Autowired
  TeamsChannelService teamsChannelService;

  @PostMapping(value = "update/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@PathVariable("token") String token,
                                       @RequestHeader Map<String, String> headers,
                                       @RequestBody NotificationBody teamsUpdate) {

      logger.info("Received message from teams");
//      Gson gson = new Gson();
//      JsonObject obj = gson.fromJson(teamsUpdate, JsonObject.class);
//
      logger.info("{}", teamsUpdate.toJson());
//
//      obj.remove("summary");
//      obj.addProperty("summary", "IMMANEWSUMMARY");

      logger.info("{}", teamsUpdate.getWebHookParams().getWebHookUrls().get(0).getUrl());

      Object obj = teamsChannelService.generatePayload(teamsUpdate.getNotification(), teamsUpdate.getWebHookParams().getWebHookUrls().get(0).getUrl());

      logger.info("{}", obj);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("CARD-UPDATE-IN-BODY",
              "true");

      return ResponseEntity.ok()
              .headers(responseHeaders)
              .body(obj.toString());
  }
}
