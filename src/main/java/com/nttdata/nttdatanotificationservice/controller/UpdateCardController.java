package com.nttdata.nttdatanotificationservice.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsFact;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsPotentialActions;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UpdateCardController {
  Logger logger = LoggerFactory.getLogger(UpdateCardController.class);

  @Autowired
  NotificationServiceProperties notificationServiceProperties;

  @PostMapping(value = "update/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@PathVariable("token") String token,
                                       @RequestHeader Map<String, String> headers,
                                       @RequestBody String teamsUpdate) {

      logger.info("Received message from teams");
      Gson gson = new Gson();
      JsonObject obj = gson.fromJson(teamsUpdate, JsonObject.class);
      obj.remove("summary");
      obj.addProperty("summary", "IMMANEWSUMMARY");
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("CARD-UPDATE-IN-BODY",
              "true");

      return ResponseEntity.ok()
              .headers(responseHeaders)
              .body(gson.toJson(obj));
  }
}
