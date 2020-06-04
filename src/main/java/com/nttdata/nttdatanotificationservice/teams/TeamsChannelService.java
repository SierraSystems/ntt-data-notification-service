package com.nttdata.nttdatanotificationservice.teams;

import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
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
    public Object generatePayload(Notification notification) {

        TeamsCard teamsCard = TeamsCard.defaultNttCard(notification.getAppName());

        teamsCard.addSection(getTeamsSection(notification));

        TeamsPotentialActions potentialActionsStatus = TeamsPotentialActions.defaultTeamsPotentialActions("ActionCard","Update Status");

        potentialActionsStatus.addInput(getTeamsInput());

        TeamsAction statusAction = TeamsAction.defaultTeamAction("HttpPOST", "OK","http://ntt-data-norification-service.canadacentral.azurecontainer.io:8080/notification/update/3ac241ab-4448-4f1a-b925-29ac7fe6e37e/aHR0cHM6Ly9vdXRsb29rLm9mZmljZS5jb20vd2ViaG9vay80NWFiNGU1OS1lNTFiLTRkODktOGE0MC02NDgzNzVmMTk3NjdANjVlNGUwNmYtZjI2My00YzFmLWJlY2ItOTBkZWI4YzJkOWZmL0luY29taW5nV2ViaG9vay9hMDEzNjNjZTg2MmU0ODkzOGM5MjNlNjAxMTA5NTNlYi83NTdkYzE0Yi1lOTY5LTQ4MGYtOTAyMy03NWFhNGMyZTdmMTU");

        potentialActionsStatus.addAction(statusAction);

        teamsCard.addPotentialAction(getTeamsPotentialActionLink("View in Splunk", notification.getReturnUrl()));

        teamsCard.addPotentialAction(potentialActionsStatus);

        statusAction.setBody(teamsCard.toJson());

        return  teamsCard;
    }

    @SuppressWarnings("java:S1602")
    private TeamsSection getTeamsSection(Notification notification) {
        TeamsSection teamsSection = TeamsSection.defaultNttSection(notification.getAppName(), notification
            .getOrigin());

        teamsSection.addFact(new TeamsFact("App", notification.getAppName()));
        teamsSection.addFact(new TeamsFact("Search", notification.getOrigin()));
        teamsSection.addFact(new TeamsFact("Owner", notification.getOwner()));

        notification.getDetails().forEach((key, value) -> {
            teamsSection.addFact(new TeamsFact(key, value.toString()));
        });

        teamsSection.addFact(new TeamsFact("Message", notification.getMessage()));
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
