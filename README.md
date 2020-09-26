# Pacman

Pacman is a single player game, where the player has three chances to complete the level and win the game.  


## How to run the Java application (game)
1.
2.
3.
4.
5.


## How to start the game

Once you have the application successfully running, you will be greeted with the game's main menu. From there, click on the option "Play Game" to start playing.


## How to play

### Player objective

As the player, your objective will be to control Pacman to eat all of the edibles spread throughout the maze and win the game. There is no time limit enforced.
However, there will be four ghosts who will try to chase Pacman within the maze. Unless the ghosts are in a "frightened" state, you will have to avoid collisions between Pacman and the ghosts. Such collisions will "kill" Pacman, and if he "dies" thrice, you will lose the game. 

### Controlling Pacman

You can use the following keys to control Pacman and move him within the maze:

1. W -> Move Up
2. S -> Move Down
3. A -> Move Left
4. D -> Move Right


## More about the ghosts

There are four ghosts in the game: "Red," "Orange," "Green" and "Purple." Each of them has unique sets of movements that will vary depending on their state and their proximity to Pacman at any point in the game. 

### Red

Red will start at the "Scattered" state in the game, when he will scatter to the maze's bottom left area. He will remain on this state for 7 seconds before transitioning to a "Chase" state for the next 20 seconds, when he will directly chase Pacman. Once the 20 seconds are over, Red will transition back to the "Scattered" state and continue the same transition cycle three more times, after which he will permanently be in the "Chase" state.

### Purple

Purple will start at the "AtHome" state, when he will stay at the "ghost home" for 1 seconds. Red will start at the "Scattered" state in the game, where he will scatter to the maze's bottoom right area. He will remain on this state for 7 seconds before transitioning to a "Chase" state for the next 20 seconds. In the "Chase" state, he will try to ambush Pacman by always moving to the tile which is at a distance of 2 tile widths from Pacman. Once the 20 seconds are over, Purple will transition back to the "Scattered" state and continue the same transition cycle three more times, after which he will indefinitely chase Pacman.

### Green 

Green will start at the "AtHome" state, when he will stay at the "ghost home" for 15 seconds. Then he will switch to the "Scattered" state, where he will scatter to the maze's bottom left area. He will remain on this state for 7 seconds before transitioning to a "Chase" state for the next 20 seconds. In the "Chase" state, he will always move towards the tile which is at twice the distance between Pacman and Red at any point in the game. Once the 20 seconds are over, Green will transition back to the "Scattered" state and continue the same transition cycle three more times, after which he will permanently be in the "Chase" state.

### Orange

Orange will start at the "AtHome" state, when he will stay at the "ghost home" for 10 seconds. Then he will switch to the "Scattered" state, where he will scatter to the maze's top left area. He will remain on this state for 7 seconds before transitioning to a "Chase" state for the next 20 seconds. In the "Chase" state, he will first calculate the distance between him and Pacman. If this distance is more than 8 tile widths he will directly chase Pacman until the distance is reduced to less than 8 tile widths, after which he will scatter back to the maze's top left area. Once the 20 seconds are over, Orange will transition back to the "Scattered" state and continue the same transition cycle three more times, after which he will permanently be in the "Chase" state.












![](images/levelDesign.png)
