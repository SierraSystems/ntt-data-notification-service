package com.nttdata.nttdatanotificationservice.controller;

import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UpdateCardController {
  Logger logger = LoggerFactory.getLogger(UpdateCardController.class);

  @Autowired
  NotificationServiceProperties notificationServiceProperties;

  @PostMapping(value = "update/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@PathVariable("token") String token, @RequestBody Object teamsUpdate) {

      logger.info(teamsUpdate.toString());

      return new ResponseEntity<>("Did a thing", HttpStatus.OK);
  }
}
