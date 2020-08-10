package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import org.springframework.http.ResponseEntity;

public interface WebHookService {
    ResponseEntity<String> postMessage(Notification notification, WebHookParams webHookParams);
}
