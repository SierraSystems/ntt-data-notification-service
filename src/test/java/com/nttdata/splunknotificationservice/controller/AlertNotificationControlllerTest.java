package com.nttdata.splunknotificationservice.controller;

import com.nttdata.splunknotificationservice.configuration.SplunkProperites;
import com.nttdata.splunknotificationservice.service.WebHookService;
import com.nttdata.splunknotificationservice.splunk.models.SplunkAlert;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AlertNotificationControlllerTest {

    private static final String FAILURE = "FAILURE";
    private static final String TEST = "test";
    @Mock
    SplunkProperites splunkProperites;

    @Mock
    WebHookService webHookService;

    @InjectMocks
    AlertNotificationController alertNotificationController = new AlertNotificationController();
    @BeforeEach
    void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        when(splunkProperites.getTokens()).thenReturn(Arrays.asList("test","test2"));
    }

    @DisplayName("Success - AlertNotificationController")
    @Test
    void testSuccess() {
        SplunkAlert splunkAlert = new SplunkAlert();
        when(webHookService.postMessage(any(), any())).thenReturn(new ResponseEntity<>(
                "We good", HttpStatus.OK));
        ResponseEntity<String> result = alertNotificationController.alert(TEST, "", splunkAlert);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Unauthorized - AlertNotificationController")
    @Test
    void testUnauthorized() {
        SplunkAlert splunkAlert = new SplunkAlert();
        when(webHookService.postMessage(any(), any())).thenReturn(new ResponseEntity<>(
                "We good", HttpStatus.OK));
        ResponseEntity<String> result = alertNotificationController.alert(FAILURE, "", splunkAlert);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
