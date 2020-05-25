package com.nttdata.nttdatanotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TeamsCard {

  private static final String TYPE = "MessageCard";
  private static final String CONTEXT = "http://schema.org/extensions";
  private static final String THEME_COLOR = "0076D7";

  private String type;
  private String context;
  private String themeColor;
  private String summary;

  private List<TeamsSection> sections = new ArrayList<>();
  private List<TeamsPotentialActions> potentialAction = new ArrayList<>();


  private TeamsCard(String type, String context, String themeColor, String summary) {
    this.type = type;
    this.context = context;
    this.themeColor = themeColor;
    this.summary = summary;
  }

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@context")
  public String getContext() {
    return context;
  }

  public String getThemeColor() {
    return themeColor;
  }

  public String getSummary() {
    return summary;
  }

  public List<TeamsSection> getSections() {
    return sections;
  }

  public List<TeamsPotentialActions> getPotentialAction() {
    return potentialAction;
  }


  public static TeamsCard defaultNttCard(String summary) {

      return new TeamsCard(TYPE, CONTEXT, THEME_COLOR, summary);

  }

  public void addSection(TeamsSection teamsSection) {
    this.sections.add(teamsSection);
  }

  public void addPotentialAction(TeamsPotentialActions teamsPotentialActions) {
    this.potentialAction.add(teamsPotentialActions);
  }

  public String toJson() {
      Gson jsonParser = new Gson();
      return jsonParser.toJson(this, TeamsCard.class);
  }

}
