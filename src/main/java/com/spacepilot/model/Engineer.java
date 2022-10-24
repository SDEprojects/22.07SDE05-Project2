package com.spacepilot.model;

public class Engineer extends Person {

<<<<<<< .merge_file_TCBxS9
  public Engineer(){
    super();
  }

=======
  public Engineer(){}
>>>>>>> .merge_file_3nuyea
  public Engineer(String name, Planet currentPlanet) {
    super(name, currentPlanet);
  }

  public static void repairSpacecraft(Spacecraft spacecraft) {
    int currentScHealth = spacecraft.getHealth();
    spacecraft.setHealth(currentScHealth + 1);
    System.out.println("SC health: " + spacecraft.getHealth());
  }

}
