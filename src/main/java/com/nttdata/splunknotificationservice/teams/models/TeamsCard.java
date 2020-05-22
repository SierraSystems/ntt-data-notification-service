package com.nttdata.splunknotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TeamsCard {
  private String type;
  private String context;
  private String themeColor;
  private String summary;
  private List<TeamsSection> sections;
  private List<TeamsPotentialActions> potentialAction;

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("@context")
  public String getContext() {
    return context;
  }

  @JsonProperty("@context")
  public void setContext(String context) {
    this.context = context;
  }

  public String getThemeColor() {
    return themeColor;
  }

  public void setThemeColor(String themeColor) {
    this.themeColor = themeColor;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<TeamsSection> getSections() {
    return sections;
  }

  public void setSections(
      List<TeamsSection> sections) {
    this.sections = sections;
  }

  public List<TeamsPotentialActions> getPotentialAction() {
    return potentialAction;
  }

  public void setPotentialAction(
      List<TeamsPotentialActions> potentialAction) {
    this.potentialAction = potentialAction;
  }
}
