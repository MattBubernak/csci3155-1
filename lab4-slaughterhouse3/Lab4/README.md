Lab 4
=====

Pull request
------------
Your group should issue a pull request with a task list included in the comment by midnight on Friday, November 15. (To clear up any misunderstanding, you do not need to issue a new pull request upon completion unless you close the WIP request.)

Due Dates
---------
- **Pull request**: Friday, November 15, midnight MST
- **Completion**: Tuesday, December 3, midnight MST (fall break is Nov. 25 - 29!)  
- **Reassessment**: Friday, December 13, the last day you'll be able to turn in *any* coursework for credit

Things to complete
------------------
- Collections warm-up (#2 in lab4.pdf)
- Type checker for small-step interpreter (#3 in lab4.pdf)
- Small-step interpreter update (#4 in lab4.pdf)

Lab4.pdf is from a previous semester; you can find any and all semantics sets included in it in Dr. Chang's lecture notes as well. Don't worry about the survey or anything else included in the .pdf other than the warmup, the type checker, and updating the small-step interpreter.

Hints
-----
For completion of this lab, you will only need to edit Lab4.scala, not the AST, parser, or any of the other included codes. The type checker should look very similar to big-step semantics.  The only major difference is that we do not perform the actual computations.  Type errors are thrown whenever we attempt to perform computation on inputs with incompatible types.  

Higher-order functions are awesome for dealing with argument / parameter lists and object fields; these could be very useful for Function, Call, Object and GetField. Built-in Scala functions you should consider using include map, foldLeft, zipped, foreach, and mapValues.

Multi-argument function calls are similar to single-argument function calls: a key difference is we now have a list of substitutions rather than a single substitution.  

Instructions
------------
In the file /src/main/scala/Lab4.scala, modify *typeInfer*, *step* and *substitute*, and also finish the higher-order functions / Collections warmup.  This lab is strongly typed so will behave differently in some cases than the previous group interpreter lab.

Write at least one javascripty test case and save it in the directory *jsy_testcases*.  This test case should test for behavior more complex than the **sbt** test cases; very simple test cases (e.g., 1+2) will not receive a very high grade. Sample javascripty files are included in *example_jsy* and *jsy_testcases*.  Test cases which are just copies of these files or copies of the FlatSpec cases get no credit.

A reasonable test case should be 5 or more lines and experiment with the interaction of many parts of the interpreter, e.g. as stated above, 1+2 isn't a very good test case because we're only looking at Plus and toNumber.

### Your group's completed assignment will include:  

1. The Collections warmup implemented in *Lab4.scala* and

2. finishing out *Lab4.scala*: fill in the skeleton in */src/main/scala/* so that the lab, including type checker and updated small-step interpreter, compiles and runs without error, plus

3.  at least one reasonably complex Javascripty test case should be saved in *jsy_testcases/*.

sbt
---
An sbt script (build.sbt) is included. Commands you might find useful include `sbt compile` to compile, `sbt clean` to clean up your compilation. `sbt "run --debug file.jsy"` runs the interpreter in debug mode with file.jsy as input.  The optional --debug flag provides informative messages throughout interpretation of the input file. `sbt test` executes the initial FlatSpec test suite.  Notice the test suite is designed to test the function of individual portions of your code, and *not necessarily the interaction between*.

If somehow you do not yet have a copy of sbt, visit scala-sbt.org.

Readings
---------
Dr. Chang's notes are the most relevant for this assignment.  The Friedman and Wand chapters discuss scoping and binding of variables, if you are interested in more details.

-Dr. Chang's course notes - Chapter 3.3.3 covers type checking.

-Odersky, Spoon and Venners, *Programming in Scala*, 
		Chapter 16.1 - 16.9 (Covers Lists),   
		Chapter 17.2 (Covers Sets and Maps)  

-Twitter Scala School - http://twitter.github.com/scala_school/collections.html

Also, if you are interested in covariance, contravariance, and invariance, you may also want to look at
- https://github.com/jrmcclurg/csci3155/blob/master/scala_example/src/main/scala/Variance.scala
- http://skipoleschris.blogspot.com/2011/06/invariance-covariance-and.html 
- http://docs.scala-lang.org/tutorials/tour/unified-types.html

If either TA comes across further readings we will post them to Piazza.

node.js
-------
While node is still useful in this lab for making sure your javascripty programs are valid, you should not mimic its behaviour this time. A correctly implemented Lab 4 interpreter instance should perfectly emulate the operational semantics.

jsy.js
------
*jsy.js* is a script to allow you to run *.jsy* files (and produce AST output) in node using `node jsy.js file.jsy`.

Remember, your interpreter *should behave differently than nodejs*--your interpreter is now strongly typed, unlike most popular varieties of Javascript.
