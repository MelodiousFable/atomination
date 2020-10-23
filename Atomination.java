import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class Atomination {
  
  public static void help() {
    System.out.println("HELP  displays this help message\n"
            + "QUIT  quits the current game\n"
            + "\n"
              + "DISPLAY  draws the game board in terminal\n"
              + "START  <number of players> <width> <height> starts the game\n"
              + "PLACE  <x> <y> places an atom in a grid space\n"
              + "UNDO  undoes the last move made\n"
              + "STAT  displays game statistics\n"
              + "SAVE  <filename> saves the state of the game\n"
              + "LOAD  <filename> loads a save file");
  }
  public static void quit() {
    System.out.println("Bye!");
    System.exit(0);
  }
  
  public static void store(int x, int y, ArrayList<Integer> l) {
    l.add(x);
    l.add(y);
    l.add(0);
    l.add(0);
  }
  
  public static int place(Integer x, Integer y, GridMap gameGrid, Player[] players, ArrayList<Integer> saveData, int turn) {
    if(x < 0 || x >= gameGrid.getWidth() || y < 0 || y >= gameGrid.getHeight()) {
      return -2;
    }
    if(gameGrid.getMap()[x][y].getOwner() == null) {
      gameGrid.getMap()[x][y].setOwner(players[turn]);
      gameGrid.getMap()[x][y].addAtom();
      players[turn].incrementGrids();
      players[turn].move();
      store(x, y, saveData);
    }
    else{
      if(players[turn] != gameGrid.getMap()[x][y].getOwner()) {
        return -1;
      }
      else {
        gameGrid.getMap()[x][y].addAtom();
        players[turn].move();
        store(x, y, saveData);
      }
    }
    gameGrid.cleanGrid();
    for(Player p : players) {
      p.eliminate();
    }
    turn += 1;
    if(turn == players.length) {
      turn = 0;
    }
    while(players[turn].isEliminated()) {
      turn += 1;
      if(turn == players.length) {
        turn = 0;
      }
    }
    return 0;
  }
  
  public static void display(GridMap gameGrid) {
    System.out.println();
    System.out.print("+");
    for(int x = 0; x < gameGrid.getMap()[0].length; x++) {
      if(x == gameGrid.getMap()[0].length - 1) {
        System.out.print("--");
      }
      else {
        System.out.print("---");
      }
    }
    System.out.println("+");
    for(int i = 0; i < gameGrid.getMap().length; i++) {
      for(int j = 0; j < gameGrid.getMap()[i].length; j++) {
        System.out.print("|" + gameGrid.getMap()[i][j]);
      }
      System.out.println("|");
    }
    System.out.print("+");
    for(int x = 0; x < gameGrid.getMap()[0].length; x++) {
      if(x == gameGrid.getMap()[0].length - 1) {
        System.out.print("--");
      }
      else {
        System.out.print("---");
      }
    }
    System.out.println("+");
    System.out.println();
  }
  
  public static Player[] getPlayerList(int n) {
    Player[] players = new Player[n];
    for(int x = 0; x < n; x++) {
      if(x == 0) {
        players[x] = new Player("Red", "R");
      }
      if(x == 1) {
        players[x] = new Player("Green", "G");
      }
      if(x == 2) {
        players[x] = new Player("Purple", "P");
      }
      if(x == 3) {
        players[x] = new Player("Blue", "B");
      }
    }
    return players;
  }
  
  public static void stat(Player[] players) {
    for(Player p : players) {
      if(p.checkEliminated()) {
        System.out.println("Player " + p.getColour() + ":");
        System.out.println("Lost");
      }
      else {
        System.out.println("Player " + p.getColour() + ":");
        System.out.println("Grid Count: " + p.getGridsOwned());
      }
      System.out.println();
    }
  }
  
  public static void main(String[] args) {
    Player[] players = null;
    boolean gameStarted = false;
    boolean loaded = false;
    GridMap gameGrid = null;
    int turn = 0;
    ArrayList<Integer> saveData = new ArrayList<Integer>();
    Scanner input = new Scanner(System.in);
    while(true) {
      boolean valid = false;
      String in = input.nextLine();
      String[] line = in.split("\\s");
      String command = line[0];
      command = command.toUpperCase();
      
      if(command.equals("HELP")) {
        valid = true;
        help();
        System.out.println();
      }
      if(command.equals("QUIT")) {
        quit();
        System.out.println();
      }
      
      if(command.equals("START")) {
        valid = true;
        if(line.length < 4) {
          System.out.println("Missing Argument");
          System.out.println();
          continue;
        }
        if(line.length > 4) {
          System.out.println("Too Many Arguments");
          System.out.println();
          continue;
        }
        if(gameStarted) {
          System.out.println("Invalid Command");
          System.out.println();
          continue;
        }
        
        Integer n, width, height;
        try {
          n = Integer.parseInt(line[1]);
          width = Integer.parseInt(line[2]);
          height = Integer.parseInt(line[3]);
        }
        catch(java.lang.NumberFormatException e) {
          System.out.println("Invalid Arguments");
          System.out.println();
          continue;
        }
        
        if(n < 1 || n > 4 || width < 2 || width > 255 || height < 2 || height > 255) {
          System.out.println("Invalid command arguments");
          System.out.println();
          continue;
        }
        else {
          gameStarted = true;
          saveData.add(width);
          saveData.add(height);
          saveData.add(n);
          players = getPlayerList(n);
          gameGrid = new GridMap(width, height, players);
          System.out.println("Game Ready");
          System.out.println("Red's Turn");
          System.out.println();
        }
      }
      if(command.equals("STAT")) {
        valid = true;
        if(!gameStarted) {
          System.out.println("Game Not In Progress");
          System.out.println();
          continue;
        }
        else {
          stat(players);
        }
      }
      if(command.equals("DISPLAY")) {
        valid = true;
        if(!gameStarted) {
          System.out.println("Game Not In Progress");
          System.out.println();
          continue;
        }
        display(gameGrid);
      }
      if(command.equals("PLACE")) {
        valid = true;
        if(!gameStarted) {
          System.out.println("Game Not In Progress");
          System.out.println();
          continue;
        }
        if(line.length != 3) {
          System.out.println("Invalid Coordinates");
          System.out.println();
          continue;
        }
        
        Integer y, x;
        
        try {
          y = Integer.parseInt(line[1]); 
          x = Integer.parseInt(line[2]);
        }
        catch(java.lang.NumberFormatException e) {
          System.out.println("Invalid Arguments");
          System.out.println();
          continue;
        }
        
        int code = place(x, y, gameGrid, players, saveData, turn);
        if(gameGrid.checkWinner() != null) {
          System.out.println(gameGrid.checkWinner() + " Wins!");
          System.exit(0);
        }
        if(code == 0) {
          turn += 1;
          if(turn == players.length) {
            turn = 0;
          }
          while(players[turn].isEliminated()) {
            turn += 1;
            if(turn == players.length) {
              turn = 0;
            }
          }
          System.out.println(players[turn] + "'s Turn");
          System.out.println();
        }
        if(code == -1) {
          System.out.println("Cannot Place Atom Here");
          System.out.println();
          continue;
        }
        if(code == -2) {
          System.out.println("Invalid Coordinates");
          System.out.println();
          continue;
        }
      }
      
      if(command.equals("UNDO")) {
        valid = true;
        if(!gameStarted) {
          System.out.println("Game Not In Progress");
          System.out.println();
          continue;
        }
        if(saveData.size() == 3) {
          System.out.println("Cannot Undo");
          System.out.println();
          continue;
        }
        int index = 3;
        Player[] newPlayers = getPlayerList(saveData.get(2));
        ArrayList<Integer> dummy = new ArrayList<Integer>();
        GridMap newMap = new GridMap(saveData.get(0), saveData.get(1), newPlayers);
        turn = 0;
        while(index < saveData.size() - 4) {
          int x = saveData.get(index);
          int y = saveData.get(index + 1);
          place(x, y, newMap, newPlayers, dummy, turn);
          turn += 1;
          if(turn == newPlayers.length) {
            turn = 0;
          }
          while(newPlayers[turn].isEliminated()) {
            turn += 1;
            if(turn == newPlayers.length) {
              turn = 0;
            }
          }
          index += 4;
        }
        saveData.remove(saveData.size() - 1);
        saveData.remove(saveData.size() - 1);
        saveData.remove(saveData.size() - 1);
        saveData.remove(saveData.size() - 1);
        players = newPlayers;
        System.out.println(players[turn] + "'s Turn");
        gameGrid = newMap;
        System.out.println();
      }
      
      if(!valid) {
        System.out.println("Invalid Command");
        System.out.println();
      }
    }
  }

}