package com.nttdata.nttdatanotificationservice.teams.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamsAction {
  @JsonProperty("@type")
  private String defaultType;
  private String name;
  private String target;
  private String body;

  private TeamsAction(String defaultType, String name, String target) {
    this.defaultType = defaultType;
    this.name = name;
    this.target = target;
  }


  public String getDefaultType() {
    return defaultType;
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
