package com.spacepilot.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import com.spacepilot.model.Engineer;
import com.spacepilot.model.Game;
import com.spacepilot.model.Person;
import com.spacepilot.model.Spacecraft;
import com.spacepilot.view.View;

public class Controller {

  private final View view; // view, which is in charge of displaying (printing) game info
  private final BufferedReader reader; // buffered reader used to read in what user enters

  private String userInput; // variable used to save user input
  private Game game; // model, where the current state of the game is stored

  public Controller(Game game, View view, BufferedReader reader) {
    this.userInput = "";
    this.game = game;
    this.view = view;
    this.reader = reader;
  }

  public String getUserInput() {
    return userInput;
  }

  public void play() throws IOException {
    // set up game environment (placing random number of astronauts, etc)
    setUp();
    // display game's introduction with flash screen and story
    gameIntro();
    while (!game.isOver()) { // While game is not over
      // print current game info
      updateView();
      // prompt the user to enter their next command (saved as userInput)
      getUserInput("Please enter your next command");
      // parse the user input to get their command (verb and noun)
      String[] userCommand = textParser(userInput);
      // execute their command and/or display information (e.g., list of commands, invalid command, etc.)
      nextMove(userCommand);
    }
  }

  public void setUp() {
    // place random number of astronauts on each planet
<<<<<<< .merge_file_vR5dnK
    for (Planet planet : game.getPlanets()) {
      planet.placeAstronauts(planet);
    }
    game.countTotalNumberOfAstronautsOnPlanet();
    // create a new spacecraft instance for the current game
    game.setSpacecraft(new Spacecraft());
    // set the current spacecraft's current planet to be Earth
    game.getSpacecraft().setCurrentPlanet(returnPlanet("earth"));
=======
    game.getMoon().placeAstronauts(game.getMoon());
    game.getMars().placeAstronauts(game.getMars());
    game.getMercury().placeAstronauts(game.getMercury());
>>>>>>> .merge_file_5PPMnB
  }

  public void gameIntro() throws IOException {
    view.getGameTextJson();
    // display title
    View.printTitle();
    // prompt the user to press "y" to continue
    do {
      getUserInput("Enter y to continue");
    } while (!userInput.equals("y"));
    View.clearConsole(); // Note: clear console does not work on IntelliJ console
    // display introductory background story
    View.printIntro();
    // prompt the user to press "y" to continue
    do {
      getUserInput("Enter y to continue");
    } while (!userInput.equals("y"));
    View.clearConsole(); // Note: clear console does not work on IntelliJ console
    // display game instructions (how-to-play)
    view.printInstructions();
  }

  public void nextMove(String[] command) throws IOException {
    if (command[0].equals("quit")) {
      game.setOver(true);

    } else if (command[0].equals("help")) {
      view.printInstructions();

// TODO: save and continue broken with the lastest change

//    } else if (command[0].equals("save")) {
//      saveGame(game);

//    } else if (command[0].equals("continue")) {
//      loadSavedGame();

    } else if (command[0].equals("go")) {
      // if the user is trying to go to the current planet
      if (command[1].equals(game.getSpacecraft().getCurrentPlanet().getName())) {
        View.printSamePlanetAlert();
      } else {
<<<<<<< .merge_file_vR5dnK
        Planet destinationPlanet = returnPlanet(command[1]);
        String event = destinationPlanet.randomEncounter();
        Spacecraft SR22T = game.getSpacecraft();
        if (event != null) {
          // decrement spacecraft health by 1.
          SR22T.setHealth(SR22T.getHealth() - 1);
          // alert the user about the event
          view.printEventAlert(event);
        }
        SR22T.setCurrentPlanet(returnPlanet(command[1]));
=======
        moveSpacecraft(command[1]);
>>>>>>> .merge_file_5PPMnB
        // decrement remaining days by 1 when user goes somewhere
        game.setRemainingDays(game.getRemainingDays() - 1);
      }

    } else if (command[0].equals("chat")) {
      userInput = "";
      while (userInput.length() < 1) {
        System.out.println("The passengers aren't doing well...");
        // display line below until user inputs at least one char
        getUserInput("What would you like to say to them?");
      }

    } else if (command[0].equals("repair")) {
      game.getSpacecraft().typeAndNumOfPassengersOnBoard();
      int engineerCount = game.getSpacecraft().getNumOfEngineersOnBoard();
      if (engineerCount == 0) {
        View.printNoEngineerAlert();
        return;
      }
      Engineer.repairSpacecraft(game.getSpacecraft());
      game.setShipHealth(game.getSpacecraft().getHealth());

    } else if (command[0].equals("load")) {
      loadNewPassengers();

    } else if (command[0].equals("unload")) {
      unloadPassengersOnEarth();

    } else { // invalid command message
      System.out.println("Invalid Command! Please use the command HELP for the ship's command log");
    }
  }

  public void loadNewPassengers() {
    Collection<Person> arrayOfAstronautsOnCurrentPlanet =
        game.getSpacecraft().getCurrentPlanet().getArrayOfAstronautsOnPlanet();
    if (arrayOfAstronautsOnCurrentPlanet.size() <= 0) {
      View.printNoAstronautsToLoad();
    }
    if (game.getSpacecraft().getCurrentPlanet().getName().equals("Earth")) {
      View.printCannotRemovePeopleFromEarth();
    }
    if (arrayOfAstronautsOnCurrentPlanet.size() > 0 && !game.getSpacecraft().getCurrentPlanet()
        .getName().equals("Earth")) {
      game.getSpacecraft().addPassengers(arrayOfAstronautsOnCurrentPlanet);
      arrayOfAstronautsOnCurrentPlanet.clear();
    }
  }

  public void unloadPassengersOnEarth() {
    // TODO: Move these print line statements to View after debugging
    Spacecraft bermoona = game.getSpacecraft();
    if (bermoona.getCurrentPlanet().getName().equals("Earth")) {
      System.out.println(
          "bermoona psngr array size before unloading: " + bermoona.getPassengers().size());
      game.getEarth().getArrayOfAstronautsOnPlanet().addAll(bermoona.getPassengers());
      System.out.println("Earth passenger array size after spacecraft unloaded: " + game.getEarth()
          .getArrayOfAstronautsOnPlanet().size());
      bermoona.getPassengers().clear();
      System.out.println(
          "bermoona psngr array size after cleared: " + bermoona.getPassengers().size());

<<<<<<< .merge_file_vR5dnK
    if (currentPlanet.getName().equals("Earth")) {
      currentPlanet.getArrayOfAstronautsOnPlanet().addAll(game.getSpacecraft().getPassengers());
      spacecraft.getPassengers().clear();
      determineIfUserWinsOrLoses();
      game.setOver(true);
=======
>>>>>>> .merge_file_5PPMnB
    } else {
      View.printYouCantUnloadPassengersIfCurrentPlanetNotEarth();
    }
  }

  public void determineIfUserWinsOrLoses() {
    int numberOfPassengersOnEarthAfterUnloading = game.getPlanets()[0].getArrayOfAstronautsOnPlanet()
        .size();
    //assuming Earth is the first planet in the array from JSON
    int totalNumberOfPersonsCreatedInSolarSystem = game.getTotalNumberOfAstronauts();
    if ((double) numberOfPassengersOnEarthAfterUnloading / totalNumberOfPersonsCreatedInSolarSystem
        >= (double) 4 / 5) {
      View.printGameOverMessage(true);
    } else {
      View.printGameOverMessage(false);
    }

  }

  public void moveSpacecraft(String destination) {
    switch (destination) {
      case "moon":
        game.getSpacecraft().setCurrentPlanet(game.getMoon());
        break;
      case "mars":
        game.getSpacecraft().setCurrentPlanet(game.getMars());
        break;
      case "mercury":
        game.getSpacecraft().setCurrentPlanet(game.getMercury());
        break;
      case "earth":
        game.getSpacecraft().setCurrentPlanet(game.getEarth());
        break;
    }
  }

  public void getUserInput(String prompt) throws IOException {
    // clear previous user input
    userInput = "";
    // print the prompt message
    View.printUserInputPrompt(prompt);
    // sanitize user response (turn it into lower-case and trim whitespaces) and save it to userInput
    userInput = reader.readLine().trim().toLowerCase();
  }

<<<<<<< .merge_file_vR5dnK
  public void displayGameState() {
    view.printGameState(game.calculateRemainingAstronautsViaTotalNumOfAstronauts(),
        game.getRemainingDays(),
        game.getSpacecraft().getHealth(), game.getSpacecraft().getCurrentPlanet().getName(),
        game.getSpacecraft().getPassengers().size());
=======
  public void updateView() {
    view.printGameState(game.getRemainingAstronauts(), game.getRemainingDays(),
        game.getShipHealth(),
        game.getSpacecraft().getCurrentPlanet().getName());
  }

  private static String[] textParser(String text) {
    String[] result = new String[2];
    String[] splitText = text.split(" ");
    String verb = splitText[0]; // First word
    String noun = splitText[splitText.length - 1]; // Last word
    result[0] = verb;
    result[1] = noun;
    return result;
>>>>>>> .merge_file_5PPMnB
  }

  public void loadSavedGame() {
    try (Reader reader = Files.newBufferedReader(Paths.get("./game-data.json"))) {
      Game savedGame = new Gson().fromJson(reader, Game.class);
      if (savedGame != null) { // if there is a saved game data
        game = savedGame;
        View.printLoadGameResult(true);
      } else {
        View.printLoadGameResult(false);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void saveGame(Game game) throws IOException {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    FileWriter writer = new FileWriter("game-data.json");
    writer.write(gson.toJson(game));
    writer.close();
    View.printSaveGameMessage();
  }

<<<<<<< .merge_file_vR5dnK
  /*
  HELPER METHODS
   */
  private static String[] textParser(String text) {
    String[] result = new String[2];
    String[] splitText = text.split(" ");
    String verb = splitText[0]; // First word
    String noun = splitText[splitText.length - 1]; // Last word
    result[0] = verb;
    result[1] = noun;
    return result;
  }

  public Planet returnPlanet(String destination) {
    // capitalize the destination
    String planetName =
        destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();
    return Arrays.stream(game.getPlanets()).filter(planet -> planet.getName().equals(planetName))
        .findFirst().orElse(null);
  }



=======
>>>>>>> .merge_file_5PPMnB
}
