public class GridMap {
  private Grid[][] map;
  private int width;
  private int height;
  private Player[] playerList;
  private Player forcedWinner;
  
  public GridMap(int width, int height, Player[] playerList) {
    this.width = width;
    this.height = height;
    this.playerList = playerList;
    this.map = new Grid[width][height];
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        if(i == 0 || i == width - 1) {
          if(j == 0 || j == height - 1) {
            this.map[i][j] = new Grid(2, this, i, j);
          }
          else {
            this.map[i][j] = new Grid(3, this, i, j);
          }
        }
        else {
          if(j == 0 || j == height - 1) {
            this.map[i][j] = new Grid(3, this, i, j);
          }
          else {
            this.map[i][j] = new Grid(4, this, i, j);
          }
        }
      }
    }
  }
  public Grid[][] getMap() { return this.map; }
  public int getHeight() { return this.height; }
  public int getWidth() { return this.width; }
  public Player[] getPlayers() { return this.playerList; }
  public void cleanGrid() {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        if(this.map[i][j].getAtomCount() == 0) {
          this.map[i][j].setOwner(null);
        }
      }
    }
  }
  public Player checkWinner() {
    if(this.forcedWinner != null) {
      return this.forcedWinner;
    }
    int elims = 0;
    Player winner = null;
    int numPlayers = this.playerList.length;
    for(Player p : this.playerList) {
      if(p.checkEliminated()) {
        elims += 1;
      }
      else {
        winner = p;
      }
    }
    if(elims == numPlayers - 1) {
      return winner;
    }
    return null;
  }
  
  public void forceWin(Player p) {
    this.forcedWinner = p;
  }
  
}