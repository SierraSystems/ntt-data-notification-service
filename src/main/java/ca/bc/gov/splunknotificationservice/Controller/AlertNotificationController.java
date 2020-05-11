package ca.bc.gov.splunknotificationservice.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertNotificationController {

    @PostMapping(value = "alert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alert() {
        return null;
    }
}
