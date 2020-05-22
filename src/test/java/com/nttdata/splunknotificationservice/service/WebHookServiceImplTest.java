package com.nttdata.splunknotificationservice.service;

import com.google.gson.Gson;
import com.nttdata.splunknotificationservice.controller.AlertNotificationController;
import com.nttdata.splunknotificationservice.splunk.models.SplunkAlert;
import com.nttdata.splunknotificationservice.splunk.models.SplunkResult;
import com.nttdata.splunknotificationservice.splunk.models.SplunkWebHookParams;
import com.nttdata.splunknotificationservice.splunk.models.SplunkWebHookUrls;
import org.junit.jupiter.api.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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


    @DisplayName("Success - WebHookServiceImpl")
    @Test
    void testSuccess() {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\"test\":\"test\"}");
        mockResponse.addHeader("content-type: application/json;");
        mockResponse.setResponseCode(200);
        mockBackEnd.enqueue(mockResponse);

        SplunkWebHookUrls splunkWebHookUrl = new SplunkWebHookUrls();
        splunkWebHookUrl.setChatApp(ChatApp.TEAMS);
        splunkWebHookUrl.setUrl(baseUrl);
        List<SplunkWebHookUrls> splunkWebHookUrls = Arrays.asList(splunkWebHookUrl);

        SplunkWebHookParams splunkWebHookParams = new SplunkWebHookParams();
        splunkWebHookParams.setSplunkWebHookUrls(splunkWebHookUrls);
        Gson gson = new Gson();
        String encodedString = Base64.getUrlEncoder().encodeToString(gson.toJson(splunkWebHookParams).getBytes());

        SplunkAlert splunkAlert = new SplunkAlert();
        splunkAlert.setApp("TEST");
        splunkAlert.setOwner("TEST");
        SplunkResult splunkResult = new SplunkResult();
        splunkResult.setMessage("AMESSAGE");
        splunkResult.setSource("ASOURCE");
        splunkAlert.setResult(splunkResult);
        splunkAlert.setResults_link("TEST");
        splunkAlert.setSid("TEST");

        ResponseEntity<String> result = webHookService.postMessage(splunkAlert,encodedString);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}
