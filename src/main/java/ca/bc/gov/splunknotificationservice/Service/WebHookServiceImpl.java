package ca.bc.gov.splunknotificationservice.Service;

import ca.bc.gov.splunknotificationservice.Model.RocketMessage;
import ca.bc.gov.splunknotificationservice.Model.SplunkAlert;
import ca.bc.gov.splunknotificationservice.Model.TeamsMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WebHookServiceImpl implements WebHookService {
    private final String teamsUrl = "";
    private final String rocketUrl = "";

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert) {
        post(teamsUrl, mapTeams(splunkAlert));
        post(rocketUrl, mapRocket(splunkAlert));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String post(String url, Object postObj) {
        RestTemplate restTemplate = new RestTemplate();
        return  restTemplate.postForObject(url, postObj, String.class);
    }


    private RocketMessage mapRocket(SplunkAlert splunkAlert) {
        RocketMessage  rocketMessage = new RocketMessage();
        rocketMessage.setText(splunkAlert.getResults_link());
        rocketMessage.setAlias("POC Error");
        return rocketMessage;
    }
    private TeamsMessage mapTeams(SplunkAlert splunkAlert) {
        TeamsMessage teamsMessage = new TeamsMessage();
        teamsMessage.setText(splunkAlert.getResults_link());
        teamsMessage.setSummary("POC Error");
        return teamsMessage;
    }
}
