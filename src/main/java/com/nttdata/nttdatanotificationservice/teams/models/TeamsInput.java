package com.nttdata.nttdatanotificationservice.teams.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TeamsInput {
  @SerializedName("@type")
  private String type;
  private String id;
  private Boolean isMultiline;
  private String title;
  private Boolean isMultiSelect;
  private ArrayList<TeamsChoice> choices = new ArrayList<>();


  public TeamsInput(String id, String type, String title) {
    this.type = type;
    this.id = id;
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public Boolean getMultiline() {
    return isMultiline;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getMultiSelect() {
    return isMultiSelect;
  }

  public List<TeamsChoice> getChoices() {
    return choices;
  }

  public void addChoice(TeamsChoice choice) {
    this.choices.add(choice);
  }

  public static TeamsInput defaultTeamsInput(String id, String type, String title) {
    return new TeamsInput(id, type, title);
  }
}
