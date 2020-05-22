package ca.bc.gov.splunknotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.ArrayList;

public class TeamsInput {
  private String type;
  private String id;
  private Boolean isMultiline;
  private String title;
  private Boolean isMultiSelect;
  private ArrayList<TeamsChoice> choices;

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@type")
  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getMultiline() {
    return isMultiline;
  }

  public void setMultiline(Boolean multiline) {
    isMultiline = multiline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getMultiSelect() {
    return isMultiSelect;
  }

  public void setMultiSelect(Boolean multiSelect) {
    isMultiSelect = multiSelect;
  }

  public ArrayList<TeamsChoice> getChoices() {
    return choices;
  }

  public void setChoices(
      ArrayList<TeamsChoice> choices) {
    this.choices = choices;
  }
}
