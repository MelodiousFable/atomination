# Atomination #
A very simple tic-tac-toe like game called Atomination.

## Running the game ##
Unfrtunately, exporting this application to a .exe standalone file does not work, as the application will not run. Therefore, 
in order to run this application, you must have the lastest version of Processing installed, and open AtominationGUI.pde with
Processing. To play the game, you must click the Run button.

## How to play ##
The game board consists of an 10x6 gridboard, and is to be played with 2 players. Each player takes turns placing an "atom"
in a grid of their choice. A grid square can hold a varying number of atoms depending on their location:
Corner squares: 1 atom
Edge squares: 2 atoms
Centre squares: 3 atoms

A player cannot place an atom in a square occupied by another player's atom, but they can place multiple atoms in a square
occupied by existing atoms of their colour. When a square at its maximum capacity gains another atom, it "explodes" and
spreads one atom to each neighbouring square, leaving the initial square empty. When a square explodes, all neighbouring
squares become owned by the player which caused the explosion, and any atoms in that square change ownership if those 
squares were previously occupied by another player.

A player wins once there are no squares occupied by the opposing player.
