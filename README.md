# Data Structures Lab 2

In this assignment you will use recursion to convert prefix expressions directly to postfix expressions. You may not use a stack as you did in Lab 1.
Write a program that accepts a prefix expression containing single letter operands and the operators +, -, \*, /, and $ (representing exponentiation). Output the corresponding postfix expression.

For example, if your input is \*AB then output should be AB\*. Output of BA\* is considered incorrect.

# Pre-Reqs
* Java 11
* Maven v3.6.x

# Build Code

You can download the source code from git like so (if provided the package as part of the assignment, skip this step):

```
git clone git@github.com:zbialik/prefix-to-postfix.git
```

You can then build the code by executing:

```
cd prefix-to-postfix
mvn clean
mvn compile
mvn package
```

The compiled jar file will be located in `target/prefix-to-postfix-0.0.1-SNAPSHOT.jar`


# Run Compiled Code

The compiled code requires 2 input arguments:
1. `inputFile` (relative or absolute path)
2. `outputFile` (relative or absolute path)

I have provided 1 test input file: `inputs.txt`.

You can run the compiled code like so:

```
java -cp prefix-to-postfix-0.0.1-SNAPSHOT.jar prefix_to_postfix.PrefixToPostfixProcessor inputs.txt outputs.txt
```

The output file will look similar to the following:

```
---------------------------------------------
-+ABC converts to postfix: AB+C-

---------------------------------------------
-A+BC converts to postfix: ABC+-

---------------------------------------------
^+-ABC+D-EF converts to postfix: AB-C+DEF-+^

---------------------------------------------
-*A^B+C-DE*FG converts to postfix: ABCDE-+^*FG*-

---------------------------------------------
**A+BC+C-BA converts to postfix: ABC+*CBA-+*

---------------------------------------------
prefix /A+BC+C*BA could not be converted: prefix prematurely closed at index: 4 (operand: C)

---------------------------------------------
*-*-ABC+BA converts to postfix: AB-C*BA+-*
```
