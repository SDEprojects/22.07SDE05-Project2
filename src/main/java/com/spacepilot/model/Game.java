package com.spacepilot.model;

//import com.spacepilot.model.
public class Game {

  private boolean isOver;
  private int remainingDays;
  private int shipHealth; // TODO: Spacecraft has its own health so this is redundant
  private Spacecraft spacecraft;
<<<<<<< .merge_file_JtHvdm

  private int totalNumberOfAstronauts;

  private Planet[] planets;
=======
  private Planet earth;
  private Planet moon;
  private Planet mars;
  private Planet mercury;
>>>>>>> .merge_file_eL0IHg

  public boolean isOver() { // Getter for isOver
    return isOver;
  }

  public void setOver(boolean over) {
    isOver = over;
  }

  public int getRemainingDays() {
    return remainingDays;
  }

  public void setRemainingDays(int remainingDays) {
    this.remainingDays = remainingDays;
  }

  public int getShipHealth() {
    return shipHealth;
  }

  public void setShipHealth(int shipHealth) {
    this.shipHealth = shipHealth;
  }

  public Spacecraft getSpacecraft() {
    return spacecraft;
  }

  public Planet getEarth() {
    return earth;
  }

  public Planet getMoon() {
    return moon;
  }

<<<<<<< .merge_file_JtHvdm
  public int getTotalNumberOfAstronauts() {
    return totalNumberOfAstronauts;
  }

  public Planet[] getPlanets() {
    return planets;
=======
  public Planet getMars() {
    return mars;
>>>>>>> .merge_file_eL0IHg
  }

  public Planet getMercury() {
    return mercury;
  }

  public void countTotalNumberOfAstronautsOnPlanet() {
    for (Planet planet : getPlanets()) {
      totalNumberOfAstronauts += planet.getArrayOfAstronautsOnPlanet().size();
    }
  }

  public int calculateRemainingAstronautsViaTotalNumOfAstronauts() {
    int totalNumberOfAstronauts = getTotalNumberOfAstronauts();
    int numberOfAstronautsOnSc = spacecraft.getPassengers().size();
    int remainingNumberOfAstronautsToPickUp = totalNumberOfAstronauts - numberOfAstronautsOnSc;
    return remainingNumberOfAstronautsToPickUp;
  }

}
