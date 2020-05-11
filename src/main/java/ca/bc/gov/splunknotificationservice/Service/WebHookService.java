package ca.bc.gov.splunknotificationservice.Service;

import ca.bc.gov.splunknotificationservice.Model.SplunkAlert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert);
}
