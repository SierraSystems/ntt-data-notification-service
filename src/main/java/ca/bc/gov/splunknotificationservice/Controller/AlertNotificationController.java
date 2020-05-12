package ca.bc.gov.splunknotificationservice.Controller;

import ca.bc.gov.splunknotificationservice.Model.SplunkAlert;
import ca.bc.gov.splunknotificationservice.Service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertNotificationController {

    @Autowired
    WebHookService webHookService;

    Logger logger = LoggerFactory.getLogger(AlertNotificationController.class);

    @PostMapping(value = "alert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alert(@RequestBody SplunkAlert splunkAlert) {
        return webHookService.postMessage(splunkAlert);
    }
}
