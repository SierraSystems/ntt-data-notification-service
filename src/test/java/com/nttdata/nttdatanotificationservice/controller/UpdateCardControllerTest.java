package com.nttdata.nttdatanotificationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nttdata.nttdatanotificationservice.configuration.NotificationBody;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;
import com.nttdata.nttdatanotificationservice.configuration.WebHookUrls;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UpdateCardControllerTest {
    @InjectMocks
    UpdateCardController updateCardController = new UpdateCardController();

    @Mock
    NotificationServiceProperties notificationServiceProperties;


  private String appName;
  private String origin;
  private String owner;
  private String message;
  private String returnUrl;

  private static final String notificationJson = "{\n" +
      "\t\"appName\" : \"app_name\",\n" +
      "\t\"origin\" : \"origin\",\n" +
      "\t\"owner\" : \"owner\",\n" +
      "\t\"message\": \"message\",\n" +
      "\t\"returnUrl\": \"return_url\"\n" +
      "}";

    @BeforeEach
    void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Success - UpdateCardController")
    @Test
    void testSuccess() {
      Gson gson = new Gson();
      Notification notification = gson.fromJson(notificationJson, Notification.class);

      NotificationBody notificationBody = new NotificationBody();
      notificationBody.setNotification(notification);
      WebHookParams webHookParams = new WebHookParams();
      webHookParams.addWebHookUrls(new WebHookUrls(ChatApp.TEAMS, "webHookUrl"));
      notificationBody.setWebHookParams(webHookParams);
      notificationBody.setResponse("Success");

      ResponseEntity<String> result = updateCardController.update("TEST", new HashMap<String, String>(), notificationBody);
      Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
