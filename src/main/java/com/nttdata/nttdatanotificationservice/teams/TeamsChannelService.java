package com.nttdata.nttdatanotificationservice.teams;

import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsAction;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsChoice;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsFact;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsInput;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsPotentialActions;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsSection;
import org.springframework.stereotype.Service;

@Service
public class TeamsChannelService implements ChannelService {

    @Override
    public ChatApp getChatApp() {
        return ChatApp.TEAMS;
    }

    @Override
    public Object generatePayload(SplunkAlert splunkAlert) {

        TeamsCard teamsCard = TeamsCard.defaultNttCard(splunkAlert.getSearch_name());

        teamsCard.addSection(getTeamsSection(splunkAlert));

        TeamsPotentialActions potentialActionsStatus = TeamsPotentialActions.defaultTeamsPotentialActions("ActionCard","Update Status");

        potentialActionsStatus.addInput(getTeamsInput());

        TeamsAction statusAction = TeamsAction.defaultTeamAction("HttpPOST", "OK","https://webhook.site/b1990094-ff1a-4668-b19e-e3bbddf8a717");

        potentialActionsStatus.addAction(statusAction);

        teamsCard.addPotentialAction(getTeamsPotentialActionLink("View in Splunk", splunkAlert.getResults_link()));
        teamsCard.addPotentialAction(getTeamsPotentialActionLink("Splunk Dashboard", splunkAlert.getResult().getDashboard()));

        teamsCard.addPotentialAction(potentialActionsStatus);

        statusAction.setBody(teamsCard.toJson());

        return  teamsCard;
    }

    @SuppressWarnings("java:S1602")
    private TeamsSection getTeamsSection(SplunkAlert splunkAlert) {
        TeamsSection teamsSection = TeamsSection.defaultNttSection(splunkAlert.getSearch_name(), splunkAlert.getResult().getSource());

        teamsSection.addFact(new TeamsFact("App", splunkAlert.getResult().getSource()));
        teamsSection.addFact(new TeamsFact("Search", splunkAlert.getSearch_name()));
        teamsSection.addFact(new TeamsFact("Owner", splunkAlert.getOwner()));

        splunkAlert.getResult().getDetails().forEach((key, value) -> {
            teamsSection.addFact(new TeamsFact(key, value.toString()));
        });

        teamsSection.addFact(new TeamsFact("Message", splunkAlert.getResult().getMessage()));
        teamsSection.addFact(new TeamsFact("Status", "Open"));
        return teamsSection;
    }

    private TeamsPotentialActions getTeamsPotentialActionLink(String message, String url) {
        TeamsPotentialActions potentialActionsLink = TeamsPotentialActions.defaultTeamsPotentialActions("ViewAction", message);
        potentialActionsLink.addTarget(url);
        return potentialActionsLink;
    }

    private TeamsInput getTeamsInput() {
        TeamsInput statusInput = TeamsInput.defaultTeamsInput("statuslist", "MultichoiceInput", "Update Status");

        statusInput.addChoice(new TeamsChoice("In Progress","1"));
        statusInput.addChoice(new TeamsChoice("In Review","2"));
        statusInput.addChoice(new TeamsChoice("Closed","3"));
        return statusInput;
    }
}
