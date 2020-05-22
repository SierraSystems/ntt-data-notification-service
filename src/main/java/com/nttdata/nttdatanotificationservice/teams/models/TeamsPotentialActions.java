package com.nttdata.nttdatanotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TeamsPotentialActions {
  private String type;
  private String name;
  private List<String> target;
  private List<TeamsInput> inputs;
  private List<TeamsAction> actions;

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@type")
  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTarget() {
    return target;
  }

  public void setTarget(List<String> target) {
    this.target = target;
  }

  public List<TeamsInput> getInputs() {
    return inputs;
  }

  public void setInputs(List<TeamsInput> inputs) {
    this.inputs = inputs;
  }

  public List<TeamsAction> getActions() {
    return actions;
  }

  public void setActions(
      List<TeamsAction> actions) {
    this.actions = actions;
  }
}
