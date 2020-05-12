package ca.bc.gov.splunknotificationservice.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class TeamsCard {
  private String type;
  private String context;
  private String themeColor;
  private String summary;
  private ArrayList<TeamsSection> sections;
  private ArrayList<TeamsPotentialActions> potentialAction;

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

  public ArrayList<TeamsSection> getSections() {
    return sections;
  }

  public void setSections(
      ArrayList<TeamsSection> sections) {
    this.sections = sections;
  }

  public ArrayList<TeamsPotentialActions> getPotentialAction() {
    return potentialAction;
  }

  public void setPotentialAction(
      ArrayList<TeamsPotentialActions> potentialAction) {
    this.potentialAction = potentialAction;
  }
}
