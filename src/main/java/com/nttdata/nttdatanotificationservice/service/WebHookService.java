package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;
import org.springframework.http.ResponseEntity;

public interface WebHookService {

    public ResponseEntity<String> postMessage(GenericAlert genericAlert, String routes);
}
