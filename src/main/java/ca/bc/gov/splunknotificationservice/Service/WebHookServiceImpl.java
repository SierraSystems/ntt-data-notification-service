package ca.bc.gov.splunknotificationservice.Service;

import ca.bc.gov.splunknotificationservice.Configuration.SplunkProperites;
import ca.bc.gov.splunknotificationservice.Model.Rocket.RocketMessage;
import ca.bc.gov.splunknotificationservice.Model.Splunk.SplunkAlert;
import ca.bc.gov.splunknotificationservice.Model.Teams.TeamsCard;
import ca.bc.gov.splunknotificationservice.Model.Teams.TeamsFact;
import ca.bc.gov.splunknotificationservice.Model.Teams.TeamsMessage;
import ca.bc.gov.splunknotificationservice.Model.Teams.TeamsPotentialActions;
import ca.bc.gov.splunknotificationservice.Model.Teams.TeamsSection;
import com.google.gson.Gson;
import java.text.MessageFormat;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableConfigurationProperties(SplunkProperites.class)
public class WebHookServiceImpl implements WebHookService {

    private static final String ROCKETCHATTEMPLATE = "App: %s \n Search: %s \n Owner: %s \n Link: %s ";
    @Autowired
    SplunkProperites splunkProperites;
    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);
    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String rocketUrl, String teamsUrl) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(splunkAlert);
        logger.info(jsonInString);

        if (rocketUrl != null) {
            logger.info("Posting message to Rocket Chat");
            post(rocketUrl, mapRocket(splunkAlert));
        }

        if (teamsUrl != null) {
            logger.info("Posting card to teams");
            //replace with map card
            post(teamsUrl, mapTeamsCard(splunkAlert));
        }
        //TODO: Does splunk care about return?
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
        String text = String.format(ROCKETCHATTEMPLATE, splunkAlert.getApp(), splunkAlert.getSearch_name(), splunkAlert.getOwner(), splunkAlert.getResults_link());
        rocketMessage.setText(text);
        rocketMessage.setAlias(splunkProperites.getRocketMessageAlias());
        return rocketMessage;
    }
    private TeamsMessage mapTeams(SplunkAlert splunkAlert) {
        TeamsMessage teamsMessage = new TeamsMessage();
        teamsMessage.setText(splunkAlert.getResults_link());
        teamsMessage.setSummary(splunkAlert.getSearch_name());
        return teamsMessage;
    }
    private TeamsCard mapTeamsCard(SplunkAlert splunkAlert) {
        TeamsCard teamsCard = new TeamsCard();
        teamsCard.setType("MessageCard");
        teamsCard.setContext("http://schema.org/extensions");
        teamsCard.setThemeColor("0076D7");
        teamsCard.setSummary(splunkAlert.getSearch_name());

        TeamsSection teamsSection = new TeamsSection();
        teamsSection.setActivityTitle(splunkAlert.getSearch_name());
        teamsSection.setActivitySubtitle(MessageFormat.format("From {0}", splunkAlert.getApp()));
        teamsSection.setActivityImage("https://teamsnodesample.azurewebsites.net/static/img/image4.png");

        TeamsFact teamsFactApp = new TeamsFact();
        teamsFactApp.setName("App");
        teamsFactApp.setValue(splunkAlert.getApp());

        TeamsFact teamsFactSearch = new TeamsFact();
        teamsFactSearch.setName("Search");
        teamsFactSearch.setValue(splunkAlert.getSearch_name());

        TeamsFact teamsFactOwner = new TeamsFact();
        teamsFactOwner.setName("Owner");
        teamsFactOwner.setValue(splunkAlert.getOwner());

        TeamsFact teamsFactLink = new TeamsFact();
        teamsFactLink.setName("Link");
        teamsFactLink.setValue(splunkAlert.getResults_link());

        ArrayList<TeamsFact> facts = new ArrayList<TeamsFact>();
        facts.add(teamsFactApp);
        facts.add(teamsFactSearch);
        facts.add(teamsFactOwner);
        facts.add(teamsFactLink);

        teamsSection.setFacts(facts);
        teamsSection.setMarkdown(true);

        ArrayList<TeamsSection> sections = new ArrayList<TeamsSection>();
        sections.add(teamsSection);

        teamsCard.setSections(sections);

        TeamsPotentialActions potentialActionsLink = new TeamsPotentialActions();
        potentialActionsLink.setType("ViewAction");
        potentialActionsLink.setName("View in Splunk");
        ArrayList<String> link = new ArrayList<String>();
        link.add(splunkAlert.getResults_link());
        potentialActionsLink.setTarget(link);

        ArrayList<TeamsPotentialActions> potentialActions = new ArrayList<TeamsPotentialActions>();
        potentialActions.add(potentialActionsLink);

        teamsCard.setPotentialAction(potentialActions);

        return  teamsCard;
    }
}
