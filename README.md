#Knight's Path
Implements pathfinding of a knight on a chess board using Dijkstra's algorithm.
Knight travels from its start position to the end position taking the shortest path.

##Compiling
javac Main.java

##Running
java Main boardWidth boardHeight startX startY endX endY

###Example
$ java Main 0 0 1 1
Initializing game board
Creating links
Generating path weights
0  3  2  3  2  3  4  5

3  4  1  2  3  4  3  4

2  1  4  3  2  3  4  5

3  2  3  2  3  4  3  4

2  3  2  3  4  3  4  5

3  4  3  4  3  4  5  4

4  3  4  3  4  5  4  5

5  4  5  4  5  4  5  6

Finding path
START -> (0,0) -> (1,2) -> (2,4) -> (0,3) -> (1,1) -> END
0                     

   4                  

   1                  

3                     

      2               

                      

                      

                      
##TODO
Use grid so it's easier to see.
