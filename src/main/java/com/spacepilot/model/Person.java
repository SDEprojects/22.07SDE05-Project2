package com.spacepilot.model;

public class Person {

  private String name;
  private Planet currentPlanet;

  Person(){}
<<<<<<< .merge_file_n0mtXP

  Person(String name, String planetName) {
=======
  Person(String name, Planet currentPlanet) {
>>>>>>> .merge_file_iQ7FMc
    this.name = name;
    this.currentPlanet = currentPlanet;
  }

  public String getName() {
    return name;
  }

  public Planet getCurrentPlanet() {
    return currentPlanet;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCurrentPlanet(Planet currentPlanet) {
    this.currentPlanet = currentPlanet;
  }

}
