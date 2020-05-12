package ca.bc.gov.splunknotificationservice.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamsAction {
  private String type;
  private String name;
  private String target;

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

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }
}
