package ca.bc.gov.splunknotificationservice.Controller;

import ca.bc.gov.splunknotificationservice.Model.SplunkAlert;
import ca.bc.gov.splunknotificationservice.Service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertNotificationController {

    @Autowired
    WebHookService webHookService;

    @PostMapping(value = "alert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alert(@RequestParam SplunkAlert splunkAlert) {
        return webHookService.postMessage(splunkAlert);
    }
}
