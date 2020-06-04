package com.nttdata.nttdatanotificationservice.controller;

import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
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

    @BeforeEach
    void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Success - UpdateCardController")
    @Test
    void testSuccess() {
        ResponseEntity<String> result = updateCardController.update("TEST", "URL", new HashMap<String, String>(), TeamsCard.defaultNttCard("",""));
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
