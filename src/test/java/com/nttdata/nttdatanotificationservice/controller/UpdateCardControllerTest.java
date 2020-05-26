package com.nttdata.nttdatanotificationservice.controller;

import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsMessage;
import org.junit.Assert;
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

import static org.mockito.Mockito.when;

public class UpdateCardControllerTest {
    @InjectMocks
    UpdateCardController updateCardController = new UpdateCardController();

    @Mock
    NotificationServiceProperties notificationServiceProperties;

    @BeforeEach
    void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Success - UpdateCardController")
    @Test
    void testSuccess() {
        ResponseEntity<String> result = updateCardController.update("TEST", new TeamsMessage());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
