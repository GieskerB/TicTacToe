# TicTacToe v5

## History

I finally want wo finish with this project. One of my first projects ever was to create the game of tic-tac-toe. Over
the years I made some changes and added new features.

### Version overview:

1. Simple base-game: 1v1 on a 3 by 3 grid. (quiet inefficient but not bad for a first project I would say)
2. Added a computer as the enemy. Only Human vs. Computer which was using the minmax algorithm, so you could not win
   anymore
3. Added the ability to use a $n \times n$ grid $\forall n \in [2,8]$. In addition, I moved the gameplay from the
   console to a JFrame window.
4. Long story short: I planed too much any the project got way to complicated, so I abandoned it for good.
5. This project will fix the problems form **v4** by starting from scratch and using testdriven development to make sure
   everything works as intended.

## Features of v5

### Gameplay

- Variable grid size from $2\times2$ upto $8\times8$.
- Four different game modes:
  - PvP
  - PvC
  - CvP
  - CvC
- Three different difficulties for the computer:
  - Easy (Computer makes random moves)
  - Medium (???)
  - Hard (Computer uses minimax to play always the best move possible)
- 

### Code

- JUnit tests for all key classes:
  - Board
- Automatic computer player for each game mode.

### JFrame

- Slider for grid size
- Extra settings window for game mode and difficulty