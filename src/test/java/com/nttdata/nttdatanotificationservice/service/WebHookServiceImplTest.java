package com.nttdata.nttdatanotificationservice.service;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.rocket.RocketChannelService;
import com.nttdata.nttdatanotificationservice.sources.generic.ConvertToGeneric;
import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkResult;
import com.nttdata.nttdatanotificationservice.sources.WebHookParams;
import com.nttdata.nttdatanotificationservice.sources.WebHookUrls;
import com.nttdata.nttdatanotificationservice.teams.TeamsChannelService;
import org.junit.jupiter.api.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class WebHookServiceImplTest {
    public static MockWebServer mockBackEnd;
    private String baseUrl;
    @InjectMocks
    WebHookService webHookService = new WebHookServiceImpl();

    @Mock
    ChannelServiceFactory channelServiceFactory;



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


    }


    @DisplayName("Success - WebHookServiceImpl TEAMS")
    @Test
    void testTEAMSSuccess() {
        Mockito.when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new TeamsChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(200);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls();
        splunkWebHookUrl.setChatApp(ChatApp.TEAMS);
        splunkWebHookUrl.setUrl(baseUrl);
        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);
        Gson gson = new Gson();
        String encodedString = Base64.getUrlEncoder().encodeToString(gson.toJson(webHookParams).getBytes());

        SplunkAlert splunkAlert = new SplunkAlert();
        splunkAlert.setOwner("TEST");
        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        splunkResult.setDashboard("TEST");
        splunkAlert.setResult(splunkResult);
        splunkAlert.setResults_link("TEST");
        splunkAlert.setSid("TEST");
        splunkAlert.setSearch_name("");

        GenericAlert genericAlert = ConvertToGeneric.splunkToGeneric(splunkAlert);

        ResponseEntity<String> result = webHookService.postMessage(genericAlert,encodedString);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Success - WebHookServiceImpl ROCKET")
    @Test
    void testRocketSuccess() {
        Mockito.when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new RocketChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(200);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls();
        splunkWebHookUrl.setChatApp(ChatApp.ROCKET_CHAT);
        splunkWebHookUrl.setUrl(baseUrl);
        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);
        Gson gson = new Gson();
        String encodedString = Base64.getUrlEncoder().encodeToString(gson.toJson(webHookParams).getBytes());

        SplunkAlert splunkAlert = new SplunkAlert();
        splunkAlert.setOwner("TEST");
        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        splunkResult.setDashboard("TEST");
        splunkAlert.setResult(splunkResult);
        splunkAlert.setResults_link("TEST");
        splunkAlert.setSid("TEST");

        GenericAlert genericAlert = ConvertToGeneric.splunkToGeneric(splunkAlert);

        ResponseEntity<String> result = webHookService.postMessage(genericAlert,encodedString);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Error - WebHookServiceImpl TEAMS")
    @Test
    void testTEAMSChatError() {
        Mockito.when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new TeamsChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(500);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls();
        splunkWebHookUrl.setChatApp(ChatApp.TEAMS);
        splunkWebHookUrl.setUrl(baseUrl);
        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);
        Gson gson = new Gson();
        String encodedString = Base64.getUrlEncoder().encodeToString(gson.toJson(webHookParams).getBytes());

        SplunkAlert splunkAlert = new SplunkAlert();
        splunkAlert.setOwner("TEST");
        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        splunkResult.setDashboard("TEST");
        splunkAlert.setResult(splunkResult);
        splunkAlert.setResults_link("TEST");
        splunkAlert.setSid("TEST");
        splunkAlert.setSearch_name("");

        GenericAlert genericAlert = ConvertToGeneric.splunkToGeneric(splunkAlert);

        ResponseEntity<String> result = webHookService.postMessage(genericAlert,encodedString);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @DisplayName("Error - WebHookServiceImpl ROCKET")
    @Test
    void testRocketChatError() {
        Mockito.when(channelServiceFactory.getChanelService(any())).thenReturn(java.util.Optional.of(new RocketChannelService()));

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(500);
        mockBackEnd.enqueue(mockResponse);

        WebHookUrls splunkWebHookUrl = new WebHookUrls();
        splunkWebHookUrl.setChatApp(ChatApp.ROCKET_CHAT);
        splunkWebHookUrl.setUrl(baseUrl);
        List<WebHookUrls> webHookUrls = Arrays.asList(splunkWebHookUrl);

        WebHookParams webHookParams = new WebHookParams();
        webHookParams.setWebHookUrls(webHookUrls);
        Gson gson = new Gson();
        String encodedString = Base64.getUrlEncoder().encodeToString(gson.toJson(webHookParams).getBytes());

        SplunkAlert splunkAlert = new SplunkAlert();
        splunkAlert.setOwner("TEST");
        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        splunkResult.setDashboard("TEST");
        splunkAlert.setResult(splunkResult);
        splunkAlert.setResults_link("TEST");
        splunkAlert.setSid("TEST");

        GenericAlert genericAlert = ConvertToGeneric.splunkToGeneric(splunkAlert);

        ResponseEntity<String> result = webHookService.postMessage(genericAlert,encodedString);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}
