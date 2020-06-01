package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.alert.models.Alert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(Alert alert, String routes);
}
