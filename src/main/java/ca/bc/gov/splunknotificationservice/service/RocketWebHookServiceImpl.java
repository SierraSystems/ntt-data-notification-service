package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.configuration.SplunkProperites;
import ca.bc.gov.splunknotificationservice.model.rocket.RocketMessage;
import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@EnableConfigurationProperties(SplunkProperites.class)
public class RocketWebHookServiceImpl implements RocketWebHookService {

    private static final String ROCKETCHATTEMPLATE = "App: %s \n Search: %s \n Owner: %s \n Link: %s ";

    @Autowired
    SplunkProperites splunkProperites;

    Logger logger = LoggerFactory.getLogger(RocketWebHookServiceImpl.class);
    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String token, String encodedRocketUrl) {
        if (!token.equals(splunkProperites.getToken())) {
            logger.error("Token failed to validate");
            return new ResponseEntity<>("Token validation failed", HttpStatus.I_AM_A_TEAPOT);
        }


        byte[] decodedRocketUrlBytes = Base64.getUrlDecoder().decode(encodedRocketUrl);
        String decodedRocketUrl = new String(decodedRocketUrlBytes);

        if (!Strings.isNullOrEmpty(decodedRocketUrl)) {
            logger.info("Posting message to Rocket Chat");
            post(decodedRocketUrl, mapRocket(splunkAlert));
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    private void post(String url, Object postObj) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url, postObj, String.class);
        } catch (Exception e) {
            logger.error("Exception in post", e);
        }
    }


    private RocketMessage mapRocket(SplunkAlert splunkAlert) {
        RocketMessage  rocketMessage = new RocketMessage();
        String text = String.format(ROCKETCHATTEMPLATE, splunkAlert.getResult().getSource(), splunkAlert.getSearch_name(), splunkAlert.getOwner(), splunkAlert.getResults_link());
        rocketMessage.setText(text);
        rocketMessage.setAlias(splunkAlert.getSearch_name());
        return rocketMessage;
    }
}
