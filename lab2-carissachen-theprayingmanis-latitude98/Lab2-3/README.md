Lab 2-3
=====

Instructions
------------

Since you have already gotten some hands-on experience writing code to evaluate expressions, we are going to essentially skip
Lab 2 (which involves evaluating simple JavaScript expressions) and move straight into Lab 3.  Section 4 of the file lab2.pdf contains some background info about JavaScript, so please skim through this section.  The file lab3.pdf contains further information about the full "JavaScripty" language that we will be using, so please read this thoroughly (ignoring the final section about Evaluation Order).  To finish the assignment, your goal is to fill in the missing pieces in the file /src/main/scala/Lab3.scala.  You will need to modify the *eval*, *step* and *substitute* functions, changing the lines marked "TODO" by replacing the "throw new UnsupportedOperationException" placeholder with the proper functionality.  There should be no need to alter any other parts of the assignment.

You are also expected to write at least one javascripty test case.  Include this file in the directory *jsy_testcases*.  This test case should test for behavior more complex than the **sbt** test cases; very simple test cases (e.g., 1+2) are not useful.  Included in *jsy_testcases* is an example test case.  Sample javascripty files are also available in the *example_jsy* folder.  Test cases which are just copies of these files will receive no credit.

### Deliverables

Your submission should have the following items included to be considered complete:

1.  Your interpreter should be implemented as *Lab3.scala* (simply modify the given template file in */src/main/scala/*.

2.  The *jsy_testcases* directory should contain your javasripty test case(s).

### sbt

For your convenience, an sbt script (build.sbt is included).  You can issue the following commands to compile, run and test your code:

    $ sbt compile 

compiles your program

    $ sbt clean

deletes the previous compilation

    $ sbt "run --debug file.jsy"

runs the interpreter in debug mode with file.jsy as input.  The optional --debug flag provides informative messages throughout interpretation of the input file.

    $ sbt test

executes the test suite.  Note that the test suite is designed to test the function of individual terms, and not necessarily the interaction between.


### Readings

Suggested readings for this assignment are below.  Dr. Chang's notes are the most relevant for this assignment.  The Friedman and Wand chapters discuss scoping and binding of variables, if you are interested in more details.

+  Dr. Chang's Course Notes - through (and including) Chapter 3.3.2.  (Chapter 3.3.3 covers type checking, which will be part of the next lab).

+  Odersky, Spoon and Venners, *Programming in Scala*, Chapter 15.6  (Covers the Option class).

+  Friedman and Wand, *Essentials of Programming Languages*, Chapters 3.3 - 3.5

Further readings, if found, will be posted on Piazza.


### node.js

You can install the Node.js JavaScript interpreter referred to in lab2.pdf/lab3.pdf by typing

    $ sudo apt-get install nodejs

In this lab, we are varying slightly from javascript's behavior.  While node.js is useful for running your javascripty programs, you should not match its behavior.  Correctly running interpreters implement the behavior described in the operational semantics.


### jsy.js

The *jsy.js* file is a wrapper for *.jsy* files to run in node.js.  To run a javascripty file in node.js, use the following command

    $ nodejs jsy.js file.jsy


### Github repository

Once you are finished with the assignment, push your local copy to one team member's github account, and issue a pull request to your team repository on the course github.


### Hints

Here are some common issues related to this lab:

1.  You will only need to edit Lab3.scala.  You do not need to include your identikey in the object or file name.

2.  Like the eval function (big-step interpreter), the step function (small-step interpreter) returns an Expr object.  The difference is that the step function does not necessarily return a value.

3.  The operational semantics (big-step and small-step) for javascripty are given in the handout.  This representation is somewhat unfamiliar, however, *they fully describe how to implement your interpreter!*  Spend some time understanding what these semantics are saying, and how they translate into code.

4.  As before, the file ast.scala (in *src/main/scala/jsy/lab3/*) contains a list of all possible terms in the abstract syntax tree. 

5.  The **sbt** test cases only test individual operations, and don't fully test every requirement for the language.  You may want to see if you can identify corner cases not being addressed by these test cases, and write additional tests.
