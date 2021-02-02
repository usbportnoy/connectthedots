# Project
Connect the Dots game Technical Assessment
## The Game
A connect-the-dots game for two players.
Definitions
octilinear line - a horizontal, vertical, or 45° diagonal line
## Rules
The game is played on a 4x4 grid of 16 nodes.
Players take turns drawing octilinear lines connecting nodes.
Each line must begin at the start or end of the existing path, so that all lines form a continuous path.
The first line may begin on any node.
A line may connect any number of nodes.
Lines may not intersect.
No node can be visited twice.
The game ends when no valid lines can be drawn.
The player who draws the last line is the loser.

# Summary
Implemented game server in Java 11 using Spring Framework. The provided client communicates with the Game Server web endpoints. The client was provided with JS. And is also served by the Java Spring server.

The frontend maintains little to no state for the game. 
The state provided by the game server is the current player, status about the state of the game (Invalid Move, Valid Move, Game Over), and the current board.

The frontend provides the client and the board.

# Implementation
For the game logic I implemented a command pattern. Command patterns are great for modifying a single state, while keeping a record of each command. Theyre frequently used in game development.

https://en.wikipedia.org/wiki/Command_pattern

The logic behind the gameplay is using an Adjacency Matrix. This creates a lookup table of relationships between two points. Those points all together make up the board.

https://mathworld.wolfram.com/AdjacencyMatrix.html

The Game Service provided does logical work for the game, and are the entry points to change the game state. The game operators control the game state.

# Build
```
./gradlew assemble
java -jar ./build/libs/connectthedots-0.0.1-SNAPSHOT.jar
google-chrome http://localhost:8080/
```


```
Ξ connectthedots git:(main) ▶ java -version
openjdk version "11.0.9.1" 2020-11-04
OpenJDK Runtime Environment (build 11.0.9.1+1-Ubuntu-0ubuntu1.20.04)
OpenJDK 64-Bit Server VM (build 11.0.9.1+1-Ubuntu-0ubuntu1.20.04, mixed mode, sharing)
Ξ connectthedots git:(main) ▶ ./gradlew -v

Welcome to Gradle 6.7.1!

Here are the highlights of this release:
 - File system watching is ready for production use
 - Declare the version of Java your build requires
 - Java 15 support

For more details see https://docs.gradle.org/6.7.1/release-notes.html


------------------------------------------------------------
Gradle 6.7.1
------------------------------------------------------------

Build time:   2020-11-16 17:09:24 UTC
Revision:     2972ff02f3210d2ceed2f1ea880f026acfbab5c0

Kotlin:       1.3.72
Groovy:       2.5.12
Ant:          Apache Ant(TM) version 1.10.8 compiled on May 10 2020
JVM:          11.0.9.1 (Ubuntu 11.0.9.1+1-Ubuntu-0ubuntu1.20.04)
OS:           Linux 5.8.0-41-generic amd64

connectthedots git:(main) ▶ ./gradlew assemble
Starting a Gradle Daemon, 2 incompatible and 1 stopped Daemons could not be reused, use --status for details

BUILD SUCCESSFUL in 15s
4 actionable tasks: 3 executed, 1 up-to-date
Ξ connectthedots git:(main) ▶ java -jar ./build/libs/connectthedots-0.0.1-SNAPSHOT.jar
```
http://localhost:8080/
