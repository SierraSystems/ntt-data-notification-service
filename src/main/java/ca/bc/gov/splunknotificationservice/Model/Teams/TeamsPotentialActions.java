package ca.bc.gov.splunknotificationservice.Model.Teams;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class TeamsPotentialActions {
  private String type;
  private String name;
  private ArrayList<String> target;
  private ArrayList<TeamsInput> inputs;
  private ArrayList<TeamsAction> actions;

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

  public ArrayList<String> getTarget() {
    return target;
  }

  public void setTarget(ArrayList<String> target) {
    this.target = target;
  }

  public ArrayList<TeamsInput> getInputs() {
    return inputs;
  }

  public void setInputs(ArrayList<TeamsInput> inputs) {
    this.inputs = inputs;
  }

  public ArrayList<TeamsAction> getActions() {
    return actions;
  }

  public void setActions(
      ArrayList<TeamsAction> actions) {
    this.actions = actions;
  }
}
