# Data Structures Lab 4

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
1. `inputFolder` (relative or absolute path) - this will be a folder containing the data files to sort
2. `outputFolder` (relative or absolute path) - this will be a folder to store the outputted data

NOTE: the output folder must exist prior to execution (a future release will create the directory for you if necessary)

I have provided my input files in the following folder: `src/test/resources/inputs`.

You can run the compiled code like so:

```
java -cp target/sort-comparator-0.0.1-SNAPSHOT.jar sorting.Main src/test/resources/inputs src/test/resources/outputs
```

The program will read each file under the inputs folder and sort the data using the 5 types of algorithms defined in this lab. 

The program will also count the # of comparisons and # of exchanges for each sort execution.

The generated outputs will be stored in subfolders in the outputs folder provided, where each subfolder represents the sort algorithm.

Lastly, the program will also output a `report.dat` file in the outputs folder provided. It will show performance metrics for each sort algorithm and also adds a column for `SORT ORDER` that is simply the folder containing the input file. This makes it easier to analyze the data afterward, but requires that you place the data files in appropriate subfolders in the inputs directory provided. 

An example output file is shown below:

```
-----------------------------------------------------
File Input: src/test/resources/inputs/order-categories/random/ran1K.dat
Sort Name: heapsort
Description: HeapSort - Heapify array then percolate down heap sort

Data Size (N): 1000
N^2: 1000000
(N+1)*(N/2): 500500
3*N*log2(N): 29897.35285398626

Original Data: [518, 5672, 4106, 28, 5581, 9691, 8901, 8366, 7639, 9680, 7581, 8720, 8055, 8493, 3603, 1117, 1782,  ...
Sorted Data: [3, 9, 11, 13, 28, 38, 46, 77, 94, 96, 99, 107, 116, 123, 126, 128, 137, 176, 185, 189, 194, 215, 21 ...
# of Comparisons: 28608
# of Exchanges: 9036
```
