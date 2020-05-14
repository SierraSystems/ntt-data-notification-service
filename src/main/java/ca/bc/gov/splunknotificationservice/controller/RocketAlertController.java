package ca.bc.gov.splunknotificationservice.controller;

import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
import ca.bc.gov.splunknotificationservice.service.RocketWebHookService;
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
public class RocketAlertController {

    Logger logger = LoggerFactory.getLogger(RocketAlertController.class);

    @Autowired
    RocketWebHookService rocketWebHookService;

    @PostMapping(value = "alert/rocket/{token}/{encodedRocketUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alert(@PathVariable("token") String token,
                                        @PathVariable("encodedRocketUrl") String encodedRocketUrl,
                                        @RequestBody SplunkAlert splunkAlert) {

        return rocketWebHookService.postMessage(splunkAlert, token, encodedRocketUrl);
    }
}
