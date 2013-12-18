##PEP 308: The Implementation of `if, else` in Python

Python, a high level programming language, is often favored by many because of its concise syntax; a feature which allows programmers to code in fewer lines. Others claim the popularity of Python comes from its ease of use and the fact that it is simpler to learn than other languages (CPlusPlus). Python was originally implemented in 1989 by Guido van Rosum, but it wasn’t until 2005 that Python added the condition expression in the form of an `if, else` statement (Python History). Conditional expressions allow for a language to perform different actions depending on a condition that is specified by the programmer.  Inputs are evaluated to a boolean, true, or false, and the program performs varying functions depending on the output. Different programming languages have distinct ways of featuring the conditional expression in terms of syntax. Here is one way the `if, else` statement can be formatted:

````python
    <expression1> if <condition is true> else <expression2>
````

It is shocking that Python took so long to adopt the conditional expression because as a computer science major, this is one of the first things we learn in basic coding. Before Python 2.5, the version of the language that first released the feature of conditional expression, Python resorted to using `and`s and `or`s in order to compute a short circuit expression that is similar to `if, else`. Such an expression would look like this:

````python
     <condition> and <expression1> or <expression2> (PEP 308). 
````

To compare what Python users had to do before 2005 we can look at the following code:

````python
    X = <condition>
    Y = <expression1>
    Z = <expression2>
	
     X and Y or Z 
     Y if X else Z
````

The `and, or` use to compute a conditional expression often results in errors, leading to the creation of Python Enhancement Proposal (PEP) 308.

At the forefront of reasons why Python didn’t implement the conditional `if-else` statements before 2005 is a lack of consensus among developers about what syntax to use.  Because the language was young and still a work in progress, Python did not have `if, else` implemented when it was first developed, though it would have been simple to do so. When the issue was finally addressed, there were many discussions on both *python-dev* and *comp.lang.python*, leading to no true agreed upon syntax.  Developer Guido van Rossum eventually chose

The original version of this PEP proposed the following syntax:

````python
<expression1> if <condition> else <expression2>
```` 

The out-of-order arrangement was found to be too uncomfortable for many of participants in the discussion; especially when <expression1> is long, it's easy to miss the conditional while skimming.

````python
x = true_value if condition else false_value
````

This is somewhat confusing to look at because you might image that `x = true_value` would be evaluated if the condition were true, and simply false_value would be evaluated if not.

This evaluation is also still lazy just as existing Boolean expressions are. This means the order of evaluation jumps around a bit. The middle condition expression is first evaluated, and then either the true_value or false_value are evaluated, depending on what the result of the first expression was.


The current `python` syntax for `if, else` found on [python.org](http://docs.python.org/2/tutorial/controlflow.html) is as follows:

````python
if x < 0:
	x = 0
   	print 'Negative changed to zero'
elif x == 0:
   	print 'Zero' 
elif x == 1:
   	print 'Single'
else:
    print 'More'
````
In order to come up with this way of representing an `if, else` statement everyone agreed on, voting was held within the community and this what was determined.  A ternary operation was later added but it was not like the `C++` ternary that uses the `?`.  It can be found [here](http://stackoverflow.com/questions/394809/ternary-conditional-operator-in-python) and looks more like this:

````python
a if test else b
````

In terms of the principles of programming languages that we learned about in class, the establishment of `if, else` is an example of a syntactic change to the `python` language.  As one may recall from lecture, *syntax*

>is the form of the expressions, statements

in a language while *semantics*

>is the meaning of the expressions, statements

so not only did the Python community have to establish the proper *syntax* for `if, else`, they also had to make sure everyone understood its *semantics* within the language.  They did this, of course, by documenting the `if, else` expression and explaining its function and purpose.  The next logical step was for the python community to change how the language was parsed so that `if, else` could be understood by interpreters asked to run `python`.  This would entail adding *judgements forms* to documentation and the parser itself not to mention adding an `if, else` case to the *abstract syntax tree* of the python language.  They had to make sure that interpretation of the the *judgement forms* wasn't ambiguous so that the code would run as the programmer intended it to run.

Overall the addition of an `if, else` statement enhanced the readability, implementation, and functionality of Python with only minor drawbacks.  The most contended effect of this new conditional statement was a small impact on backwards compatibility to previous versions of the language.

````python  
[f for f in lambda x: x, lambda x: x**2 if f(1) == 1]
````

(I.e. a list comprehension where the sequence following 'in' is an un-parenthesized series of lambdas -- or just one lambda, even.)

In Python 3.0, the series of lambdas will have to be parenthesized, e.g.:

````python
 [f for f in (lambda x: x, lambda x: x**2) if f(1) == 1]
````

This issue arises from the looser binding of the lambda statement than the `if, else` implementation.  A similar, but more rigid issue was encountered in Python 2.5 when the `if, else` statement was originally implemented.  In this case, backwards compatibility was less of an issue, but grammar constraints involving parenthesized condition statements complicated lambda implementation.

During the original proposal of the `if, else` improvement, there were three main groups of detractors to the improvement of Python conditionals.  The first group wished to "Adopt a ternary operator built using punctuation characters:"

````python
<condition> ? <expression1> : <expression2>
````

The second group wished to "Adopt a ternary operator built using new or existing keywords."
Two examples include:

````python
<condition> then <expression1> else <expression2>
(if <condition>: <expression1> else: <expression2>)
````

And the third grouped wanted to do nothing at all (Rossum).  The first two groups were the source of most of the syntactical turmoil that delayed the implementation of an `if, else` statement, while the third simply backlogged the process as resident naysayers.  When Guido van Rossum finally accepted the proposed alteration on Friday, September 30th, 2005, detractions waned and the improved ease of conditionals and lack of major drawbacks drew most debate to a close.

With very little continued discussion about any negative impact of introducing an `if, else` conditional to the Python language, it can only be concluded that this proposed and implemented alteration was distinctly an improvement.

###Bibliography

* [The Python Tutorial](http://docs.python.org/2/tutorial/controlflow.html)
* [Ternary conditional operator in Python](http://stackoverflow.com/questions/394809/ternary-conditional-operator-in-python)
* [PEP 308-Conditional Expressions](http://www.python.org/dev/peps/pep-0308/)
* [What’s New in Python 2.5](http://docs.python.org/2/whatsnew/2.5.html)
* [CPlusPlus](http://www.cplusplus.com/forum/lounge/102966/)
* [Python History](http://python-history.blogspot.com/2009/01/brief-timeline-of-python.html)
* [Condition Expression Resolution](https://mail.python.org/pipermail/python-dev/2005-September/056846.html)
