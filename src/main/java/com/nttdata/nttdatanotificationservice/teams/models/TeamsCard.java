package com.nttdata.nttdatanotificationservice.teams.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TeamsCard {

  private static final String DEFAULT_TYPE = "MessageCard";
  private static final String DEFAULT_CONTEXT = "http://schema.org/extensions";
  private static final String THEME_COLOR = "0076D7";
  @SerializedName("@type")
  private String type;
  @SerializedName("@context")
  private String context;
  private String themeColor;
  private String summary;
  private String webHookUrl;
  private String response;

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

  public String getType() {
    return type;
  }

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

      return new TeamsCard(DEFAULT_TYPE, DEFAULT_CONTEXT, THEME_COLOR, summary, webHookUrl);

  }

  public void addSection(TeamsSection teamsSection) {
    this.sections.add(teamsSection);
  }

  public void addPotentialAction(TeamsPotentialActions teamsPotentialActions) {
    this.potentialAction.add(teamsPotentialActions);
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String toJson() {
      Gson jsonParser = new Gson();
      return jsonParser.toJson(this, TeamsCard.class);
  }

}
