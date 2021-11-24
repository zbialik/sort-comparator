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
java -cp sort-comparator-0.0.1-SNAPSHOT.jar scorpion_solitaire.Sort inputs.txt outputs.txt
```

The output will look similar to the following:

```
-----
```


