# Checkers-AI
	Team Members
1. Shanon Gao (Developer & Tester)
2. Andrew Johnston (Developer & Project Manager)
3. Vishnu Adda (Developer & Architect)
4. Shaion Habibvand (Developer)
	Software Name: Checkers
	1. Problem Statement
This software application simulates a classic checkers game, inheriting original rules of the checkers game, including:
* Moving player pieces from your side to the opponent's side
* “Jumping” over opponents pieces, thus capturing and removing the opponents piece
* Once a piece reaches the opposing side of the board, the piece becomes a king and can move bidirectionally.
* A player wins once all the opponents pieces have been captured
* If there are no moves available between both players, the game ends in a tie
2. Product Objective
The product's objective is to create a desktop system software implementation of the playable game checkers. The game should allow a user to learn how to play the game, including a textual reference to the rules and how they can interact with this implementation of checkers. The in-game mechanics should inherit all of the classic rules with respect to the pieces movements, mechanics, forfeiting, etc. There should also be a mechanism to track ranking between different players as defined by their name, including the time to completion.
3. Functional Requirements
   1. Main menu with options to:
      1. Play against a computer
      2. Play local multiplayer on the same computer
      3. View how to play
      4. View high scores
      5. Quit
   2. Play
      1. Computer:
         1. Before starting the game, the user should be prompted to select from Easy, Medium or Difficult game difficulties
         2. An algorithm for each difficulty should be created to simulate each of the aforementioned difficulties
      2. Local multiplayer
         1. When starting the game, the players should determine who will be player 1 and player 2. There should be an option to input the name for each respective player
         2. During player 1’s turn, player 2 cannot move and vise versa for when it’s player 2’s turn
      3. There should be a timer displaying minutes and seconds during the game to show how long the game has elapsed
      4. There should be an ability to pause the game, thus effectively pausing the timer and giving the player an option to:
         1. Forfeit: Quits the game and takes the player back to the main menu
         2. Resume game: Continues the timer and allows for interactions during the players turn
      5. The game should be controlled via Mouse inputs and therefore should accept left click at the least.
   3. Viewing how to play:
      1. Show the general rules in a manual format for the player to understand the rules of the game and how to interact with the pieces
      2. Ability to go back to the main menu
   4. Viewing high scores:
      1. Show a list of the top 10 users, ranked and ordered by the completion time
      2. Ability to go back to the main menu
   5. Quit, therefore close the game
