package com.nttdata.nttdatanotificationservice.controller;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.service.WebHookService;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
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

public class SplunkNotificationControlllerTest {

    private static final String FAILURE = "FAILURE";
    private static final String TEST = "TEST";

    private static final String splunkAlertJson = "{\n" +
        "\t\"result\": {\n" +
        "\t\t\"message\" : \"message\",\n" +
        "\t\t\"other\" : \"other\",\n" +
        "\t\t\"_raw\" : \"_raw\",\n" +
        "\t\t\"source\": \"source\",\n" +
        "\t\t\"dashboard\": \"dashboard_link\"\n" +
        "\t},\n" +
        "\t\"sid\" : \"sid\",\n" +
        "\t\"results_link\" : \"result_links\",\n" +
        "\t\"search_name\" : \"search_name\",\n" +
        "\t\"owner\" : \"owner\",\n" +
        "\t\"app\" : \"app\"\n" +
        "}";

    @Mock
    NotificationServiceProperties notificationServiceProperties;

    @Mock
    WebHookService webHookService;

    @InjectMocks
    SplunkNotificationController splunkNotificationController = new SplunkNotificationController();

    @BeforeEach
    void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        when(notificationServiceProperties.getTokens()).thenReturn(Arrays.asList("TEST"));
    }

    @DisplayName("Success - SplunkNotificationController")
    @Test
    void testSuccess() {
        Gson gson = new Gson();
        SplunkAlert splunkAlert = gson.fromJson(splunkAlertJson, SplunkAlert.class);

        when(webHookService.postMessage(any(), any())).thenReturn(new ResponseEntity<>(
                "Success", HttpStatus.OK));
        ResponseEntity<String> result = splunkNotificationController.alert(TEST, "", splunkAlert);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Unauthorized - SplunkNotificationController")
    @Test
    void testUnauthorized() {
        Gson gson = new Gson();
        SplunkAlert splunkAlert = gson.fromJson(splunkAlertJson, SplunkAlert.class);

        when(webHookService.postMessage(any(), any())).thenReturn(new ResponseEntity<>(
                "Success", HttpStatus.OK));
        ResponseEntity<String> result = splunkNotificationController.alert(FAILURE, "", splunkAlert);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
