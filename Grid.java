public class Grid {
  private Player owner;
  private int atomCount;
  private int maxAtoms;
  private GridMap gm;
  public int xCoord;
  public int yCoord;
  
  public Grid(int maxAtoms, GridMap gm, int x, int y) {
    this.owner = null;
    this.atomCount = 0;
    this.maxAtoms = maxAtoms;
    this.gm = gm;
    this.xCoord = x;
    this.yCoord = y;
  }
  
  public void setOwner(Player p) { this.owner = p; }
  public Player getOwner() { return this.owner; }
  public int getMax() { return this.maxAtoms; }
  public void setMax(int n) { this.maxAtoms = n; }
  public int getAtomCount() { return this.atomCount; }
  public void removeAtom() {
    this.atomCount -= 1;
  }
  public void addAtom() { 
    this.atomCount += 1;
    if(this.atomCount == this.maxAtoms) {
      try {
        this.atomCount = 0;
        this.owner.decrementGrids();
        if(this.xCoord != 0) {
          if(this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner() != null && this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner() != this.owner) {
            this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner().decrementGrids();
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord - 1][this.yCoord].getAtomCount() == 0) {
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner() != null) {
            if(this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner().getGridsOwned() == 0) {
              this.gm.getMap()[this.xCoord - 1][this.yCoord].getOwner().eliminate();
              if(this.gm.checkWinner() != null) {
                return;
              }
            }
          }
          this.gm.getMap()[this.xCoord - 1][this.yCoord].setOwner(this.owner);
          this.gm.getMap()[this.xCoord - 1][this.yCoord].addAtom();
        }
        if(this.yCoord != 0) {
          if(this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner() != null && this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner() != this.owner) {
            this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner().decrementGrids();
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord][this.yCoord - 1].getAtomCount() == 0) {
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner() != null) {
            if(this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner().getGridsOwned() == 0) {
              this.gm.getMap()[this.xCoord][this.yCoord - 1].getOwner().eliminate();
              if(this.gm.checkWinner() != null) {
                return;
              }
            }
          }
          this.gm.getMap()[this.xCoord][this.yCoord - 1].setOwner(this.owner);
          this.gm.getMap()[this.xCoord][this.yCoord - 1].addAtom();
        }
        if(this.xCoord != this.gm.getWidth() - 1) {
          if(this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner() != null && this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner() != this.owner) {
            this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner().decrementGrids();
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord + 1][this.yCoord].getAtomCount() == 0) {
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner() != null) {
            if(this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner().getGridsOwned() == 0) {
              this.gm.getMap()[this.xCoord + 1][this.yCoord].getOwner().eliminate();
              if(this.gm.checkWinner() != null) {
                return;
              }
            }
          }
          this.gm.getMap()[this.xCoord + 1][this.yCoord].setOwner(this.owner);
          this.gm.getMap()[this.xCoord + 1][this.yCoord].addAtom();
        }
        if(this.yCoord != this.gm.getHeight() - 1) {
          if(this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner() != null && this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner() != this.owner) {
            this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner().decrementGrids();
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord][this.yCoord + 1].getAtomCount() == 0) {
            this.owner.incrementGrids();
          }
          if(this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner() != null) {
            if(this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner().getGridsOwned() == 0) {
              this.gm.getMap()[this.xCoord][this.yCoord + 1].getOwner().eliminate();
              if(this.gm.checkWinner() != null) {
                return;
              }
            }
          }
          this.gm.getMap()[this.xCoord][this.yCoord + 1].setOwner(this.owner);
          this.gm.getMap()[this.xCoord][this.yCoord + 1].addAtom();
        }
      }
      catch(java.lang.StackOverflowError e) {
        gm.forceWin(this.owner);
        return;
      }
    }
  }
  
  public String toString() {
    if(this.owner == null) {
      return "  ";
    }
    return owner.getC() + atomCount;
  }
}