package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
import org.springframework.http.ResponseEntity;

public interface TeamsWebHookService {

  public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String token, String encodedTeamsUrl);
}
