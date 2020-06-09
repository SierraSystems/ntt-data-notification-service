package com.nttdata.nttdatanotificationservice.teams.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TeamsPotentialActions {
  @SerializedName("@type")
  private String type;
  private String name;
  private List<String> target = new ArrayList<>();
  private List<TeamsInput> inputs = new ArrayList<>();
  private List<TeamsAction> actions = new ArrayList<>();

  protected TeamsPotentialActions(String type, String name) {
    this.type = type;
    this.name = name;
  }


  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public List<String> getTarget() {
    return target;
  }

  public List<TeamsInput> getInputs() {
    return inputs;
  }

  public void addTarget(String target) {
    this.target.add(target);
  }

  public void addInput(TeamsInput input) {
    this.inputs.add(input);
  }

  public List<TeamsAction> getActions() {
    return actions;
  }

  public void addAction(TeamsAction action) {
    this.actions.add(action);
  }


  public static TeamsPotentialActions defaultTeamsPotentialActions(String type, String name) {
    return new TeamsPotentialActions(type, name);
  }

}
