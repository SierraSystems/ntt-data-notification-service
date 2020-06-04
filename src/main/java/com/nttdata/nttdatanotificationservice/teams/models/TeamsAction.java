package com.nttdata.nttdatanotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamsAction {

  private String type;
  private String name;
  private String target;
  private String body;

  private TeamsAction(String defaultType, String name, String target) {
    this.type = defaultType;
    this.name = name;
    this.target = target;
  }

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getTarget() {
    return target;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public static TeamsAction defaultTeamAction(String type, String name, String target) {
    return new TeamsAction(type, name, target);
  }

}
