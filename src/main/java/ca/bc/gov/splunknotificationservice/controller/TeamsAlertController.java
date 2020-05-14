package ca.bc.gov.splunknotificationservice.controller;

import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
import ca.bc.gov.splunknotificationservice.service.TeamsWebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamsAlertController {

  @Autowired
  TeamsWebHookService teamsWebHookService;

  Logger logger = LoggerFactory.getLogger(TeamsAlertController.class);

  @PostMapping(value = "alert/teams/{token}/{encodedTeamsUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> alert(@PathVariable("token") String token,
      @PathVariable("encodedTeamsUrl") String encodedTeamsUrl,
      @RequestBody SplunkAlert splunkAlert) {
    return teamsWebHookService.postMessage(splunkAlert, token, encodedTeamsUrl);
  }
}
