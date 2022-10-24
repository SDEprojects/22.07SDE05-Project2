package com.spacepilot;

import com.google.gson.Gson;
import com.spacepilot.controller.Controller;
//import com.spacepilot.model.Music;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.spacepilot.model.Game;
import com.spacepilot.view.View;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Main {

  private static Game game;

  public static void main(String[] args) {
    try (
        Reader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
    ) {
     boolean continuePlaying = true;

      while (continuePlaying) {
       // Music.playMusic();
        Game game = createNewGame(); // Model
        View view = new View(); // View
        Controller controller = new Controller(game, view, reader); // Controller
        controller.play();
        //Prompt user if another game
        controller.getUserInput("Turn & Burn! Would you like to play again?");
        //Get user input

        //If the input is n, chance continuePlaying to false
        if (controller.getUserInput().equals("n")) {
          continuePlaying = false;
        }
      }
    } catch(IOException  e){
      throw new RuntimeException(e);
    }
  }

  public static Game createNewGame() {
    // create a reader
    try (Reader reader = new InputStreamReader(
        Main.class.getResourceAsStream("new-game-data.json"))
    ) {
      // convert JSON file to Game
      return new Gson().fromJson(reader, Game.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
