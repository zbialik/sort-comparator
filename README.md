# Data Structures Lab 4

<TBD>

You should print out every move, including when the reserve is dealt, when a column is filled or when a card is turned face-up.  

# Pre-Reqs
* Java 11
* Maven v3.6.x

# Build Code

You can download the source code from git like so (if provided the package as part of the assignment, skip this step):

```
git clone git@github.com:zbialik/sort-comparator.git
cd sort-comparator
```

You can then build the code by executing:

```
mvn clean
mvn compile
mvn package
```

The compiled jar file will be located in `target/sort-comparator-0.0.1-SNAPSHOT.jar`


# Run Compiled Code

The compiled code requires 2 input arguments:
1. `inputFile` (relative or absolute path)

I have provided 1 test input file: `inputs.txt`.

You can run the compiled code like so:

```
java -cp scorpion-solitaire-0.0.1-SNAPSHOT.jar scorpion_solitaire.PlayScorpion inputs.txt outputs.txt
```

The output will look similar to the following:

```
--------------------------------------------------------
Reserve: [ ?? ?? ?? ]

Tableau:

      ??      ??      ??      ??      9S      KD      KS
      ??      ??      ??      ??      6D      QD      JC
      2H      ??      ??      8H      5D      2D        
      AH      4C     10C              4D      5S        
      7S      QH      9C              JS     10S        
      6S      5H                      3C                
      3D      4H                      2C                
      JH      3S                      AC                
     10H      2S                                        
      9H      AS                                        
      AD      6C                                        
      QC      5C                                        
              7D                                        
              8D                                        

2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1200.0)
2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1200.0)
2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 4 (output score: 2400.0)
2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1300.0)
2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1300.0)
2021-11-17 20:33:31,762 DEBUG s.Play [main] scoreMove() recurse layer: 4 (output score: 2600.0)
...
...
...
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1300.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 4 (output score: 2600.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1300.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1300.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 4 (output score: 2600.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1400.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 5 (output score: 1400.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 4 (output score: 2800.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: 4100.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: 5300.0)
2021-11-17 20:33:31,767 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: 6400.0)
--------------------------------------------------------
Reserve: [ ?? ?? ?? ]

Tableau:

      ??      ??      ??      ??      9S      KD      KS
      ??      ??      ??      ??      6D      QD      JC
      2H      ??      9D      8H      5D      2D     10C
      AH      4C                      4D      5S      9C
      7S      QH                      JS     10S        
      6S      5H                      3C                
      3D      4H                      2C                
      JH      3S                      AC                
     10H      2S                                        
      9H      AS                                        
      AD      6C                                        
      QC      5C                                        
              7D                                        
              8D                                        
```


A winning output is also shown below:

```
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,854 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,855 DEBUG s.Play [main] scoreMove() recurse layer: 3 (output score: Infinity)
2021-11-17 20:31:41,855 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,855 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
--------------------------------------------------------
Reserve: [ ]

Tableau:

                      KH      QS      KC      KD      KS
                      QH      JS      QC      QD        
                      JH     10S      JC      JD        
                     10H      9S     10C     10D        
                      9H      8S      9C      9D        
                      8H      7S      8C      8D        
                      7H      6S      7C      7D        
                      6H      5S      6C                
                      5H      4S      5C                
                      4H      3S      4C                
                      3H      2S      3C                
                      2H      AS      2C                
                      AH      6D      AC                
                              5D                        
                              4D                        
                              3D                        
                              2D                        
                              AD                        

2021-11-17 20:31:41,856 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,856 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
2021-11-17 20:31:41,856 DEBUG s.Play [main] scoreMove() recurse layer: 2 (output score: Infinity)
2021-11-17 20:31:41,856 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
--------------------------------------------------------
Reserve: [ ]

Tableau:

                      KH      QS      KC      KD      KS
                      QH      JS      QC      QD        
                      JH     10S      JC      JD        
                     10H      9S     10C     10D        
                      9H      8S      9C      9D        
                      8H      7S      8C      8D        
                      7H      6S      7C      7D        
                      6H      5S      6C      6D        
                      5H      4S      5C      5D        
                      4H      3S      4C      4D        
                      3H      2S      3C      3D        
                      2H      AS      2C      2D        
                      AH              AC      AD        

2021-11-17 20:31:41,857 DEBUG s.Play [main] scoreMove() recurse layer: 1 (output score: Infinity)
--------------------------------------------------------
Reserve: [ ]

Tableau:

                      KH              KC      KD      KS
                      QH              QC      QD      QS
                      JH              JC      JD      JS
                     10H             10C     10D     10S
                      9H              9C      9D      9S
                      8H              8C      8D      8S
                      7H              7C      7D      7S
                      6H              6C      6D      6S
                      5H              5C      5D      5S
                      4H              4C      4D      4S
                      3H              3C      3D      3S
                      2H              2C      2D      2S
                      AH              AC      AD      AS


Computer WON in Scorpion Solitaire (50 turns)!
recursion layers utilized: 5
```

Note that for the winning output, we see the identified score as Infinity for a few moves prior to the last one. This is due to the recursive algorithim implemented. The program determined the win-path 5 moves ahead (due to recursion threshold set to 5 in this example)!
