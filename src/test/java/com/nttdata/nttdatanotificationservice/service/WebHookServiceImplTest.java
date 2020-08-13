package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.rocket.RocketChannelService;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkResult;
import com.nttdata.nttdatanotificationservice.configuration.WebHookParams;
import com.nttdata.nttdatanotificationservice.configuration.WebHookUrls;
import com.nttdata.nttdatanotificationservice.teams.TeamsChannelService;
import org.junit.jupiter.api.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WebHookServiceImplTest {
    public static MockWebServer mockBackEnd;
    private String baseUrl;
    @InjectMocks
    WebHookService webHookService = new WebHookServiceImpl();

    @Mock
    ChannelServiceFactory channelServiceFactory;

    @Mock
    TeamsChannelService teamsChannelService;


    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        MockitoAnnotations.initMocks(this);
        baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        when(teamsChannelService.generatePayload(any(), any())).thenReturn(new Object());
    }


    @DisplayName("Success - WebHookServiceImpl TEAMS")
    @Test
    void testTEAMSSuccess() {
        when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(teamsChannelService));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(200);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls(ChatApp.TEAMS, baseUrl);

        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);

        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        SplunkAlert splunkAlert = new SplunkAlert("SID", "RESULTS_LINK", "SEARCH_NAME", "OWNER", splunkResult);

        Notification notification = splunkAlert.convertToAlert();

        ResponseEntity<String> result = webHookService.postMessage(notification,webHookParams);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Success - WebHookServiceImpl ROCKET")
    @Test
    void testRocketSuccess() {
        when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new RocketChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(200);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls(ChatApp.ROCKET_CHAT, baseUrl);

        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);

        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        SplunkAlert splunkAlert = new SplunkAlert("SID", "RESULTS_LINK", "SEARCH_NAME", "OWNER", splunkResult);

        Notification notification = splunkAlert.convertToAlert();

        ResponseEntity<String> result = webHookService.postMessage(notification,webHookParams);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Error - WebHookServiceImpl TEAMS")
    @Test
    void testTEAMSChatError() {
        when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(teamsChannelService));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(500);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls(ChatApp.TEAMS, baseUrl);

        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);

        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        SplunkAlert splunkAlert = new SplunkAlert("SID", "RESULTS_LINK", "SEARCH_NAME", "OWNER", splunkResult);

        Notification notification = splunkAlert.convertToAlert();

        ResponseEntity<String> result = webHookService.postMessage(notification,webHookParams);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Error - WebHookServiceImpl ROCKET")
    @Test
    void testRocketChatError() {
        when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new RocketChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(500);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls(ChatApp.ROCKET_CHAT, baseUrl);

        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);

        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        SplunkAlert splunkAlert = new SplunkAlert("SID", "RESULTS_LINK", "SEARCH_NAME", "OWNER", splunkResult);

        Notification notification = splunkAlert.convertToAlert();

        ResponseEntity<String> result = webHookService.postMessage(notification,webHookParams);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}
