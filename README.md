# Game Of Life  - Android 

_The Game of Life_, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.

## Rules

1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
2. Any live cell with two or three live neighbours lives on to the next generation.
3. Any live cell with more than three live neighbours dies, as if by over-population.
4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

## Building the project
Clone the project to your computer. The project is an Android Studio project. To build the whole project, just select "Open an existing Android Studio project" when you open Android Studio.

## How to play
The game comes with randomly generated game table. Also you can clear and draw your own patterns too. This application supports both landscape and portrait orientations.

Portrait:

![portrait](http://i.imgur.com/jQ65ocW.png?1)



Landscape:

![landscape](http://i.imgur.com/t9T8SZr.png?1)


## Development
This game includes one activity and one backgorund service. "LocalBroadcastManager" is used for commonicaiton between activity and backgroud service (GameService.java)

There is a singleton class "GameData.java" that holds game settings. 

_Observer Pattern_ is used for interactions of cells. When a cell changes its state its neighbour cells will observe this status change and keeps for next generation.

## For more information
See the wikipedia article on Conway's Game of Life: 
[https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
