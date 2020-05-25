package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String routes);
}
