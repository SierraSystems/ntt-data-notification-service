package com.nttdata.nttdatanotificationservice.controller;

import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsFact;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsPotentialActions;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<TeamsCard> update(@PathVariable("token") String token,
                                       @RequestHeader Map<String, String> headers,
                                       @RequestBody TeamsCard teamsUpdate) {

      logger.info("Received message from teams");
      TeamsPotentialActions potentialActionsLink = TeamsPotentialActions.defaultTeamsPotentialActions("ViewAction", "WAT");
      potentialActionsLink.addTarget("BLARG");
      teamsUpdate.addPotentialAction(potentialActionsLink);
      ResponseEntity<TeamsCard> res =  new ResponseEntity<TeamsCard>(teamsUpdate, HttpStatus.OK);
      res.getHeaders().add("CARD-UPDATE-IN-BODY","true");
      return res;
  }
}
