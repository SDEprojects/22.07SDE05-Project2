package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import model.Engineer;
import model.Game;
import model.Person;
import model.Spacecraft;
import view.GameText;


public class Controller {

  private String userInput; // variable used to save user input

  private final Game game; // model, where the current state of the game is stored

  private final GameText view; // view, which is in charge of displaying (printing) game info
  private final BufferedReader reader; // buffered reader used to read in what user enters

  public Controller(Game game, GameText view, BufferedReader reader) {
    this.userInput = "";
    this.game = game;
    this.view = view;
    this.reader = reader;
  }

  public Game getGame() {
    return game;
  }

  public void play() throws IOException {
    //creates engineers
    game.createEngineersHelper(game.getMars());
    game.createEngineersHelper(game.getMercury());
    game.createEngineersHelper(game.getMoon());
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
    // display game over message
    // TODO: Right now, the game is set to display the lose message only.
    //  Change this when the game's win / lose logic is implemented.
    view.printGameOverMessage(false);
  }

  public void gameIntro() throws IOException {
    view.getJsonGameText();
    // display title
    view.printTitle();
    // prompt the user to press "y" to continue
    do {
      getUserInput("Enter y to continue");
    } while (!userInput.equals("y"));
    GameText.clearConsole(); // Note: clear console does not work on IntelliJ console
    // display introductory background story
    view.printIntro();
    // prompt the user to press "y" to continue
    do {
      getUserInput("Enter y to continue");
    } while (!userInput.equals("y"));
    GameText.clearConsole(); // Note: clear console does not work on IntelliJ console
    // display game instructions (how-to-play)
    view.printInstructions();
  }


  public void nextMove(String[] command) throws IOException {
    if (command[0].equals("quit")) {
      game.setOver(true);

    } else if (command[0].equals("go")) {
      moveSpacecraft(command[1]);

    } else if (command[0].equals("help")) {
      view.printInstructions();

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
        view.printNoEngineerAlert();
        return;
      }
      Engineer engineer = new Engineer();
      engineer.repairSpacecraft(game.getSpacecraft());

      // invalid command message
    } else if (command[0].equals("load")) {
      loadNewPassengers();
    } else if (command[0].equals("unload")) {
      unloadPassengersOnEarth();
    } else {
      System.out.println("Invalid Command! Please use the command HELP for the ship's command log");
    }
  }

  public void loadNewPassengers() {

    Collection<Person> arrayOfAstronautsOnCurrentPlanet = game.getSpacecraft().getCurrentPlanet()
        .getArrayOfAstronautsOnPlanet();
    if (arrayOfAstronautsOnCurrentPlanet.size() > 0) {
      System.out.println("size of array of astros on current planet before loading: "
          + arrayOfAstronautsOnCurrentPlanet.size());
      System.out.println(
          "size of array of astros on spacecraft before loading: " + game.getSpacecraft()
              .getPassengers().size());
      game.getSpacecraft().addPassengers(arrayOfAstronautsOnCurrentPlanet);
      System.out.println(
          "size of array of astros on spacecraft AFTER loading: " + game.getSpacecraft()
              .getPassengers().size());
      arrayOfAstronautsOnCurrentPlanet.clear();
      System.out.println(
          "array of astros on current planet after loading onto SC (should be empty): "
              + arrayOfAstronautsOnCurrentPlanet);
    } else {
      System.out.println("Sorry, there are no astronauts to rescue on this planet.");
      //TODO: stop user from trying to load passengers back onto planet
      //TODO: get rid of souts after testing if engineers are created or move to view
    }

  }

  public void unloadPassengersOnEarth() {
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

    } else {
      System.out.println("Passengers can only be dropped off on Earth.");
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
    view.printUserInputPrompt(prompt);
    // sanitize user response (turn it into lower-case and trim whitespaces) and save it to userInput
    userInput = reader.readLine().trim().toLowerCase();
  }

  public void updateView() {
    view.printGameState(game.getRemainingAstro(), game.getRemainingDays(), game.getShipHealth(),
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
  }

}