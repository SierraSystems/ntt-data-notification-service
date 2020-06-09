package com.nttdata.nttdatanotificationservice.teams.models;

public class TeamsFact {
  private String name;
  private String value;

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public TeamsFact(String name, String value) {
    this.name = name;
    this.value = value;
  }

}
