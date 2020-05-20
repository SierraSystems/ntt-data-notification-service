package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.splunk.models.SplunkAlert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String routes);
}
