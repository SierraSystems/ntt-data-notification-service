package com.nttdata.splunknotificationservice.service;

import com.nttdata.splunknotificationservice.splunk.models.SplunkAlert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String routes);
}
