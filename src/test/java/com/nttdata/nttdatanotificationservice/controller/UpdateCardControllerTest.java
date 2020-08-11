package com.nttdata.nttdatanotificationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.configuration.NotificationBody;
import com.nttdata.nttdatanotificationservice.configuration.NotificationServiceProperties;
import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;
import com.nttdata.nttdatanotificationservice.configuration.WebHookUrls;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.teams.TeamsChannelService;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateCardControllerTest {
    @InjectMocks
    UpdateCardController updateCardController;

    @Mock
    TeamsChannelService teamsChannelService;

    @Mock
    NotificationServiceProperties notificationServiceProperties;


    private static final String teamsCard = "{\n"
        + "    \"@type\": \"MessageCard\",\n"
        + "    \"@context\": \"http://schema.org/extensions\",\n"
        + "    \"themeColor\": \"0076D7\",\n"
        + "    \"summary\": \"Postman\",\n"
        + "    \"webHookUrl\": \"https://outlook.office.com/webhook/45ab4e59-e51b-4d89-8a40-648375f19767@65e4e06f-f263-4c1f-becb-90deb8c2d9ff/IncomingWebhook/a01363ce862e48938c923e60110953eb/757dc14b-e969-480f-9023-75aa4c2e7f15\",\n"
        + "    \"sections\": [\n"
        + "        {\n"
        + "            \"activityTitle\": \"Postman\",\n"
        + "            \"activitySubtitle\": \"From Testing Search\",\n"
        + "            \"activityImage\": \"https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png\",\n"
        + "            \"facts\": [\n"
        + "                {\n"
        + "                    \"name\": \"App\",\n"
        + "                    \"value\": \"Postman\"\n"
        + "                },\n"
        + "                {\n"
        + "                    \"name\": \"Search\",\n"
        + "                    \"value\": \"Testing Search\"\n"
        + "                },\n"
        + "                {\n"
        + "                    \"name\": \"Owner\",\n"
        + "                    \"value\": \"admin\"\n"
        + "                },\n"
        + "                {\n"
        + "                    \"name\": \"dashboard\",\n"
        + "                    \"value\": \"Nop\"\n"
        + "                },\n"
        + "                {\n"
        + "                    \"name\": \"Message\",\n"
        + "                    \"value\": \"mongod\"\n"
        + "                },\n"
        + "                {\n"
        + "                    \"name\": \"Status\",\n"
        + "                    \"value\": \"Open\"\n"
        + "                }\n"
        + "            ],\n"
        + "            \"markdown\": true\n"
        + "        }\n"
        + "    ],\n"
        + "    \"potentialAction\": [\n"
        + "        {\n"
        + "            \"@type\": \"ViewAction\",\n"
        + "            \"name\": \"View in Splunk\",\n"
        + "            \"target\": [\n"
        + "                \"http://www.google.com\"\n"
        + "            ],\n"
        + "            \"inputs\": [],\n"
        + "            \"actions\": []\n"
        + "        },\n"
        + "        {\n"
        + "            \"@type\": \"ActionCard\",\n"
        + "            \"name\": \"Update Status\",\n"
        + "            \"target\": [],\n"
        + "            \"inputs\": [\n"
        + "                {\n"
        + "                    \"@type\": \"MultichoiceInput\",\n"
        + "                    \"id\": \"statuslist\",\n"
        + "                    \"title\": \"Update Status\",\n"
        + "                    \"choices\": [\n"
        + "                        {\n"
        + "                            \"display\": \"In Progress\",\n"
        + "                            \"value\": \"In Progress\"\n"
        + "                        },\n"
        + "                        {\n"
        + "                            \"display\": \"In Review\",\n"
        + "                            \"value\": \"2\"\n"
        + "                        },\n"
        + "                        {\n"
        + "                            \"display\": \"Closed\",\n"
        + "                            \"value\": \"3\"\n"
        + "                        }\n"
        + "                    ]\n"
        + "                }\n"
        + "            ],\n"
        + "            \"actions\": [\n"
        + "                {\n"
        + "                    \"@type\": \"HttpPOST\",\n"
        + "                    \"name\": \"OK\",\n"
        + "                    \"target\": \"https://ntt-data-notification-service.azurewebsites.net/notification/update/3ac241ab-4448-4f1a-b925-29ac7fe6e37e\",\n"
        + "                    \"body\": \"{\\\"@type\\\":\\\"MessageCard\\\",\\\"@context\\\":\\\"http://schema.org/extensions\\\",\\\"themeColor\\\":\\\"0076D7\\\",\\\"summary\\\":\\\"Postman\\\",\\\"webHookUrl\\\":\\\"https://outlook.office.com/webhook/45ab4e59-e51b-4d89-8a40-648375f19767@65e4e06f-f263-4c1f-becb-90deb8c2d9ff/IncomingWebhook/a01363ce862e48938c923e60110953eb/757dc14b-e969-480f-9023-75aa4c2e7f15\\\",\\\"sections\\\":[{\\\"activityTitle\\\":\\\"Postman\\\",\\\"activitySubtitle\\\":\\\"From Testing Search\\\",\\\"activityImage\\\":\\\"https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png\\\",\\\"facts\\\":[{\\\"name\\\":\\\"App\\\",\\\"value\\\":\\\"Postman\\\"},{\\\"name\\\":\\\"Search\\\",\\\"value\\\":\\\"Testing Search\\\"},{\\\"name\\\":\\\"Owner\\\",\\\"value\\\":\\\"admin\\\"},{\\\"name\\\":\\\"dashboard\\\",\\\"value\\\":\\\"Nop\\\"},{\\\"name\\\":\\\"Message\\\",\\\"value\\\":\\\"mongod\\\"},{\\\"name\\\":\\\"Status\\\",\\\"value\\\":\\\"Open\\\"}],\\\"markdown\\\":true}],\\\"potentialAction\\\":[{\\\"@type\\\":\\\"ViewAction\\\",\\\"name\\\":\\\"View in Splunk\\\",\\\"target\\\":[\\\"http://www.google.com\\\"],\\\"inputs\\\":[],\\\"actions\\\":[]},{\\\"@type\\\":\\\"ActionCard\\\",\\\"name\\\":\\\"Update Status\\\",\\\"target\\\":[],\\\"inputs\\\":[{\\\"@type\\\":\\\"MultichoiceInput\\\",\\\"id\\\":\\\"statuslist\\\",\\\"title\\\":\\\"Update Status\\\",\\\"choices\\\":[{\\\"display\\\":\\\"In Progress\\\",\\\"value\\\":\\\"1\\\"},{\\\"display\\\":\\\"In Review\\\",\\\"value\\\":\\\"2\\\"},{\\\"display\\\":\\\"Closed\\\",\\\"value\\\":\\\"3\\\"}]}],\\\"actions\\\":[{\\\"@type\\\":\\\"HttpPOST\\\",\\\"name\\\":\\\"OK\\\",\\\"target\\\":\\\"doathing\\\"}]}]}\"\n"
        + "                }\n"
        + "            ]\n"
        + "        }\n"
        + "    ]\n"
        + "}";


    private static final String updatedCard = "{\"@type\":\"MessageCard\",\"@context\":\"http://schema.org/extensions\",\"themeColor\":\"0076D7\",\"summary\":\"Postman\",\"webHookUrl\":\"https://outlook.office.com/webhook/45ab4e59-e51b-4d89-8a40-648375f19767@65e4e06f-f263-4c1f-becb-90deb8c2d9ff/IncomingWebhook/a01363ce862e48938c923e60110953eb/757dc14b-e969-480f-9023-75aa4c2e7f15\",\"sections\":[{\"activityTitle\":\"Postman\",\"activitySubtitle\":\"From Testing Search\",\"activityImage\":\"https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png\",\"facts\":[{\"name\":\"App\",\"value\":\"Postman\"},{\"name\":\"Search\",\"value\":\"Testing Search\"},{\"name\":\"Owner\",\"value\":\"admin\"},{\"name\":\"dashboard\",\"value\":\"Nop\"},{\"name\":\"Message\",\"value\":\"mongod\"},{\"name\":\"Status\",\"value\":\"Success\"}],\"markdown\":true}],\"potentialAction\":[{\"@type\":\"ViewAction\",\"name\":\"View in Splunk\",\"target\":[\"http://www.google.com\"],\"inputs\":[],\"actions\":[]},{\"@type\":\"ActionCard\",\"name\":\"Update Status\",\"target\":[],\"inputs\":[{\"@type\":\"MultichoiceInput\",\"id\":\"statuslist\",\"title\":\"Update Status\",\"choices\":[{\"display\":\"In Progress\",\"value\":\"In Progress\"},{\"display\":\"In Review\",\"value\":\"2\"},{\"display\":\"Closed\",\"value\":\"3\"}]}],\"actions\":[{\"@type\":\"HttpPOST\",\"name\":\"OK\",\"target\":\"https://ntt-data-notification-service.azurewebsites.net/notification/update/3ac241ab-4448-4f1a-b925-29ac7fe6e37e\",\"body\":\"{\\\"@type\\\":\\\"MessageCard\\\",\\\"@context\\\":\\\"http://schema.org/extensions\\\",\\\"themeColor\\\":\\\"0076D7\\\",\\\"summary\\\":\\\"Postman\\\",\\\"webHookUrl\\\":\\\"https://outlook.office.com/webhook/45ab4e59-e51b-4d89-8a40-648375f19767@65e4e06f-f263-4c1f-becb-90deb8c2d9ff/IncomingWebhook/a01363ce862e48938c923e60110953eb/757dc14b-e969-480f-9023-75aa4c2e7f15\\\",\\\"sections\\\":[{\\\"activityTitle\\\":\\\"Postman\\\",\\\"activitySubtitle\\\":\\\"From Testing Search\\\",\\\"activityImage\\\":\\\"https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png\\\",\\\"facts\\\":[{\\\"name\\\":\\\"App\\\",\\\"value\\\":\\\"Postman\\\"},{\\\"name\\\":\\\"Search\\\",\\\"value\\\":\\\"Testing Search\\\"},{\\\"name\\\":\\\"Owner\\\",\\\"value\\\":\\\"admin\\\"},{\\\"name\\\":\\\"dashboard\\\",\\\"value\\\":\\\"Nop\\\"},{\\\"name\\\":\\\"Message\\\",\\\"value\\\":\\\"mongod\\\"},{\\\"name\\\":\\\"Status\\\",\\\"value\\\":\\\"Open\\\"}],\\\"markdown\\\":true}],\\\"potentialAction\\\":[{\\\"@type\\\":\\\"ViewAction\\\",\\\"name\\\":\\\"View in Splunk\\\",\\\"target\\\":[\\\"http://www.google.com\\\"],\\\"inputs\\\":[],\\\"actions\\\":[]},{\\\"@type\\\":\\\"ActionCard\\\",\\\"name\\\":\\\"Update Status\\\",\\\"target\\\":[],\\\"inputs\\\":[{\\\"@type\\\":\\\"MultichoiceInput\\\",\\\"id\\\":\\\"statuslist\\\",\\\"title\\\":\\\"Update Status\\\",\\\"choices\\\":[{\\\"display\\\":\\\"In Progress\\\",\\\"value\\\":\\\"1\\\"},{\\\"display\\\":\\\"In Review\\\",\\\"value\\\":\\\"2\\\"},{\\\"display\\\":\\\"Closed\\\",\\\"value\\\":\\\"3\\\"}]}],\\\"actions\\\":[{\\\"@type\\\":\\\"HttpPOST\\\",\\\"name\\\":\\\"OK\\\",\\\"target\\\":\\\"doathing\\\"}]}]}\"}]}]}";

      @BeforeEach
      void initialize() {
          MockitoAnnotations.initMocks(this);
          when(notificationServiceProperties.getTokens()).thenReturn(Arrays.asList("TEST","test2"));
      }

      @DisplayName("Success - UpdateCardController")
      @Test
      void testSuccess() {
        Gson gson = new Gson();

        NotificationBody notificationBody = new NotificationBody();
        WebHookParams webHookParams = new WebHookParams();
        webHookParams.addWebHookUrls(new WebHookUrls(ChatApp.TEAMS, "webHookUrl"));
        notificationBody.setWebHookParams(webHookParams);
        notificationBody.setResponse("Success");

        when(teamsChannelService.generatePayload(any(), any())).thenReturn(gson.fromJson(teamsCard, TeamsCard.class));
        ResponseEntity<String> result = updateCardController.update("TEST", notificationBody);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(true, result.getHeaders().containsKey("CARD-UPDATE-IN-BODY"));
        Assertions.assertEquals("true", result.getHeaders().get("CARD-UPDATE-IN-BODY").get(0));
        Assertions.assertEquals(updatedCard, result.getBody());
      }

    @DisplayName("Failure - UpdateCardController")
    @Test
    void testMissingWebHookUrl() {
        NotificationBody notificationBody = new NotificationBody();
        notificationBody.setResponse("Success");

        ResponseEntity<String> result = updateCardController.update("TEST", notificationBody);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertEquals("Missing webHook Url.", result.getBody());
    }

    @DisplayName("Unauthorized - NotificationController")
    @Test
    void testUnauth() {
      ResponseEntity<String> result = updateCardController.update("unauthorised", new NotificationBody());
      Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
