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
  private String webHookUrl;

  private List<TeamsSection> sections = new ArrayList<>();
  private List<TeamsPotentialActions> potentialAction = new ArrayList<>();

  /***
   *
   * @param type Standard teams variable
   * @param context Standard teams variable
   * @param themeColor Standard teams variable
   * @param summary Standard teams variable
   * @param webHookUrl Non standard team variable. Used to update teams card.
   */
  private TeamsCard(String type, String context, String themeColor, String summary, String webHookUrl) {
    this.type = type;
    this.context = context;
    this.themeColor = themeColor;
    this.summary = summary;
    this.webHookUrl = webHookUrl;

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

  public String getWebHookUrl() {
    return webHookUrl;
  }

  public static TeamsCard defaultNttCard(String summary, String webHookUrl) {

      return new TeamsCard(TYPE, CONTEXT, THEME_COLOR, summary, webHookUrl);

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
