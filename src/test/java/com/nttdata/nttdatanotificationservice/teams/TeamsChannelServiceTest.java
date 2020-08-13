package com.nttdata.nttdatanotificationservice.teams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamsChannelServiceTest {
    @InjectMocks
    private TeamsChannelService sut;

    @Mock
    NotificationServiceProperties notificationServiceProperties;

    private static final String splunkAlertJson = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"message\" : \"message\",\n" +
            "\t\t\"other\" : \"other\",\n" +
            "\t\t\"source\": \"source\"\n" +
            "\t},\n" +
            "\t\"sid\" : \"sid\",\n" +
            "\t\"results_link\" : \"result_links\",\n" +
            "\t\"search_name\" : \"search_name\",\n" +
            "\t\"owner\" : \"owner\",\n" +
            "\t\"app\" : \"app\"\n" +
            "}";

    private static final String teamsResult = "{\"webHookParams\":{\"webHookUrls\":[{\"chatApp\":\"TEAMS\",\"url\":\"TESTURL\"}]},\"notification\":{\"appName\":\"source\",\"origin\":\"search_name\",\"owner\":\"owner\",\"message\":\"message\",\"returnUrl\":\"result_links\",\"details\":{\"other\":\"other\"}},\"response\":\"{{statusList.value}}\"}";

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(notificationServiceProperties.getUpdateCardBase()).thenReturn("http://aurl.com");
    }


    @Test
    @DisplayName("CASE 1: with valid splunk notification")
    public void withValidSplunkNotificationShouldProduceTeamObject()
        throws JsonProcessingException {

        Gson gson = new Gson();
        SplunkAlert splunkAlert = gson.fromJson(splunkAlertJson, SplunkAlert.class);
        // Work around to get other fields mapped. Gson wouldn't map.
        splunkAlert.getResult().setDetails("other","other");

        Notification notification = splunkAlert.convertToAlert();

        TeamsCard actual = (TeamsCard) sut.generatePayload(notification, "TESTURL");


        Assertions.assertEquals("http://schema.org/extensions", actual.getContext());
        Assertions.assertEquals("source", actual.getSummary());
        Assertions.assertEquals("0076D7", actual.getThemeColor());
        Assertions.assertEquals("MessageCard", actual.getType());
        Assertions.assertEquals(2, actual.getPotentialAction().size());


        Assertions.assertEquals("View in Splunk", actual.getPotentialAction().get(0).getName());
        Assertions.assertEquals("ViewAction", actual.getPotentialAction().get(0).getType());
        Assertions.assertEquals(0, actual.getPotentialAction().get(0).getActions().size());

        Assertions.assertEquals(0, actual.getPotentialAction().get(0).getInputs().size());


        Assertions.assertEquals("Update Status", actual.getPotentialAction().get(1).getName());
        Assertions.assertEquals("ActionCard", actual.getPotentialAction().get(1).getType());
        Assertions.assertEquals(1, actual.getPotentialAction().get(1).getActions().size());

        Assertions.assertEquals("HttpPOST", actual.getPotentialAction().get(1).getActions().get(0).getType());
        Assertions.assertEquals("http://aurl.com", actual.getPotentialAction().get(1).getActions().get(0).getTarget());
        Assertions.assertEquals("OK", actual.getPotentialAction().get(1).getActions().get(0).getName());
        Assertions.assertEquals(teamsResult, actual.getPotentialAction().get(1).getActions().get(0).getBody());



        Assertions.assertEquals(1, actual.getPotentialAction().get(1).getInputs().size());

        Assertions.assertEquals("statuslist", actual.getPotentialAction().get(1).getInputs().get(0).getId());
        Assertions.assertEquals("Update Status", actual.getPotentialAction().get(1).getInputs().get(0).getTitle());
        Assertions.assertEquals("MultichoiceInput", actual.getPotentialAction().get(1).getInputs().get(0).getType());
        Assertions.assertEquals(null, actual.getPotentialAction().get(1).getInputs().get(0).getMultiline());
        Assertions.assertEquals(null, actual.getPotentialAction().get(1).getInputs().get(0).getMultiSelect());

        Assertions.assertEquals(3, actual.getPotentialAction().get(1).getInputs().get(0).getChoices().size());

        Assertions.assertEquals("In Progress", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(0).getDisplay());
        Assertions.assertEquals("In Progress", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(0).getValue());
        Assertions.assertEquals("In Review", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(1).getDisplay());
        Assertions.assertEquals("In Review", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(1).getValue());
        Assertions.assertEquals("Closed", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(2).getDisplay());
        Assertions.assertEquals("Closed", actual.getPotentialAction().get(1).getInputs().get(0).getChoices().get(2).getValue());

        Assertions.assertEquals(1, actual.getSections().size());

        Assertions.assertEquals("https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png", actual.getSections().get(0).getActivityImage());
        Assertions.assertEquals("From search_name", actual.getSections().get(0).getActivitySubtitle());
        Assertions.assertEquals("source", actual.getSections().get(0).getActivityTitle());
        Assertions.assertEquals(6, actual.getSections().get(0).getFacts().size());

        Assertions.assertEquals("App", actual.getSections().get(0).getFacts().get(0).getName());
        Assertions.assertEquals("source", actual.getSections().get(0).getFacts().get(0).getValue());
        Assertions.assertEquals("Search", actual.getSections().get(0).getFacts().get(1).getName());
        Assertions.assertEquals("search_name", actual.getSections().get(0).getFacts().get(1).getValue());
        Assertions.assertEquals("Owner", actual.getSections().get(0).getFacts().get(2).getName());
        Assertions.assertEquals("owner", actual.getSections().get(0).getFacts().get(2).getValue());
        Assertions.assertEquals("other", actual.getSections().get(0).getFacts().get(3).getName());
        Assertions.assertEquals("other", actual.getSections().get(0).getFacts().get(3).getValue());
        Assertions.assertEquals("Message", actual.getSections().get(0).getFacts().get(4).getName());
        Assertions.assertEquals("message", actual.getSections().get(0).getFacts().get(4).getValue());
        Assertions.assertEquals("Status", actual.getSections().get(0).getFacts().get(5).getName());
        Assertions.assertEquals("Open", actual.getSections().get(0).getFacts().get(5).getValue());

        Assertions.assertEquals(true, actual.getSections().get(0).getMarkdown());

    }


}
