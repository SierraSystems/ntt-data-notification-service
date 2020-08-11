package com.nttdata.nttdatanotificationservice.controller;

import com.nttdata.nttdatanotificationservice.configuration.NotificationBody;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;
import com.nttdata.nttdatanotificationservice.service.WebHookService;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NotificationControllerTest {
    @InjectMocks
    NotificationController notificationController = new NotificationController();

    @Mock
    WebHookService webHookService;

    @Mock
    NotificationServiceProperties notificationServiceProperties;

    @BeforeEach
    void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        when(notificationServiceProperties.getTokens()).thenReturn(Arrays.asList("test","test2"));
    }
    @DisplayName("Success - NotificationController")
    @Test
    void testSuccess() {
        NotificationBody notificationBody = new NotificationBody();
        notificationBody.setNotification(new Notification());
        notificationBody.setWebHookParams(new WebHookParams());
        when(webHookService.postMessage(any(), any())).thenReturn(new ResponseEntity<>(
                "We good", HttpStatus.OK));
        ResponseEntity<String> result = notificationController.alert("test", notificationBody);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @DisplayName("Unauthorized - NotificationController")
    @Test
    void testUnauth() {
        ResponseEntity<String> result = notificationController.alert("TEST", new NotificationBody());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}

