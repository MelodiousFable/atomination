import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public class AtominationGUI extends PApplet {

    private int turn;  
    private GridMap gm;
    private Player[] players;
    private boolean winner;
    private int turncounter;

    public AtominationGUI() {
        this.turn = 0;
        this.players = new Player[2];
        this.players[0] = new Player("Red", "R");
        this.players[1] = new Player("Green", "G");
        this.gm = new GridMap(10, 6, this.players);
        this.winner = false;
        this.turncounter = 0;
    }

    public void mouseClicked(MouseEvent event) {
        boolean nextTurn = true;
        for(Grid[] gLine : gm.getMap()) {
            for(Grid g : gLine) {
                if(mouseX <= (g.xCoord + 1)*256 && mouseX > (g.xCoord)*256) {
                    if(mouseY <= (g.yCoord + 1)*256 && mouseY > (g.yCoord)*256) {
                        if(g.getOwner() == null) {
                            g.setOwner(players[turn]);
                            g.addAtom();
                            players[turn].incrementGrids();
                        }
                        else {
                            if(players[turn] != g.getOwner()) {
                                nextTurn = false;
                            }
                            else {
                                g.addAtom();
                            }
                        }
                        gm.cleanGrid();
                    }
                }
            }
        }           
        if(nextTurn) {
            turn += 1;
            this.turncounter += 1;
            if(turn == players.length) {
                turn = 0;
            }
        }
    }

    public void setup() {
        frameRate(60);
    }

    public void settings() {
        size(2560, 1536);
    }

    public void draw() {
        PImage red1 = loadImage("assets/red1.png");
        PImage red2 = loadImage("assets/red2.png");
        PImage red3 = loadImage("assets/red3.png");
        PImage green1 = loadImage("assets/green1.png");
        PImage green2 = loadImage("assets/green2.png");
        PImage green3 = loadImage("assets/green3.png");
        PImage tile = loadImage("assets/tile.png");
        
        tile.resize(256, 256);
        red1.resize(256, 256);
        red2.resize(256, 256);
        red3.resize(256, 256);
        green1.resize(256, 256);
        green2.resize(256, 256);
        green3.resize(256, 256);
        
        for(int i = 0; i < 10; i++) {
             for(int j = 0; j < 6; j++) {
                image(tile, i*256, j*256);
                if(gm.getMap()[i][j].getOwner() == null) {
                    ;
                }
                else {
                    if(gm.getMap()[i][j].getOwner().getColour() == "Red") {
                        if(gm.getMap()[i][j].getAtomCount() == 1) {
                            image(red1, i*256, j*256);
                        }
                        if(gm.getMap()[i][j].getAtomCount() == 2) {
                            image(red2, i*256, j*256);
                        }
                        if(gm.getMap()[i][j].getAtomCount() == 3) {
                            image(red3, i*256, j*256);
                        }
                    }
                    if(gm.getMap()[i][j].getOwner().getColour() == "Green") {
                        if(gm.getMap()[i][j].getAtomCount() == 1) {
                            image(green1, i*256, j*256);
                        }
                        if(gm.getMap()[i][j].getAtomCount() == 2) {
                            image(green2, i*256, j*256);
                        }
                        if(gm.getMap()[i][j].getAtomCount() == 3) {
                            image(green3, i*256, j*256);
                        }
                    }
                }
            }
        }
        if(this.turncounter > 2) {
            int r_counter = 0;
            int g_counter = 0;
            for(Grid[] gLine : gm.getMap()) {
                for(Grid g : gLine) {
                    if(g.getOwner() != null) {
                        if(g.getOwner().getColour().equals("Red")) {
                            r_counter += 1;
                        }
                        if(g.getOwner().getColour().equals("Green")) {
                            g_counter += 1;
                        }
                    }
                }
            }
            if(r_counter == 0) {
                System.out.println("Green wins!");
                noLoop();
            }
            if(g_counter == 0) {
                System.out.println("Red wins!");
                noLoop();
            }
        }
    }

    public static void go() {
        AtominationGUI.main("AtominationGUI");
    }

}