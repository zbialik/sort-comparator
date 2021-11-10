# Data Structures Lab 3

A difficult form of Solitaire, called Scorpion, can be played as follows.  A deck of 52 cards is dealt in an array of 7 rows and 7 columns with the rows overlapping. Deal across each row. Start by dealing four cards face down in the first row. Finish the row with three face up cards. Row 2 and row 3 are exactly the same as row 1. Deal rows 4-7 with all the cards face up. (See picture.) This uses 49 cards.  The 3 remaining cards are kept face down as a reserve. Play is as follows. Only the card closest to the player in each column is available.  If a face-down card becomes available, it is turned up.  Build down in suits by moving face-up cards (and everything on top of them) on top of available cards.  Nothing may be built on Aces.  If a column becomes empty, it may be filled by any king (and the cards on top of it). When further play is impossible, take the reserve cards and deal them face-up on the top of the first three columns. Continue play. The game is over when no further play is possible.  You should also be able to find a description online of this game, as well as online versions, which you can use for practice.

Write a program to implement this card game using some kind of linked list structure.  The computer should play the game, not you.  It is not required that the program select the optimal moves each time. Play several games including the one shown below. Start with this initial deal and write a shuffle function to generate additional deals.

You should print out every move, including when the reserve is dealt, when a column is filled or when a card is turned face-up.  

# Pre-Reqs
* Java 11
* Maven v3.6.x

# Build Code

You can download the source code from git like so (if provided the package as part of the assignment, skip this step):

```
git clone git@github.com:zbialik/scorpion-solitaire.git
cd scorpion-solitaire
```

You can then build the code by executing:

```
mvn clean
mvn compile
mvn package
```

The compiled jar file will be located in `target/scorpion-solitaire-0.0.1-SNAPSHOT.jar`


# Run Compiled Code

The compiled code requires 2 input arguments:
1. `inputFile` (relative or absolute path)
2. `outputFile` (relative or absolute path)

I have provided 1 test input file: `inputs.txt`.

You can run the compiled code like so:

```
java -cp scorpion-solitaire-0.0.1-SNAPSHOT.jar scorpion_solitaire.PlayScorpion inputs.txt outputs.txt
```

The output file will look similar to the following:

```
TBD
```
