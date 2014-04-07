Fork this repository (Assignments), and then clone/pull your forked copy to a folder on your local machine. Good luck!

Before you start, please do the following:

		cp -r indiv-lab0 lab0-solutions

Now go into the solutions directory and change the prefix on the copy you will be working in to your identikey.

		cd lab0-solutions
		mv indiv-lab0 kaoudis-lab0
		git add -A
		
Make all your changes in this copy, the copy outside the solutions directory is just for your reference/in case you mess up a file and need to replace it.
Don't forget to add all the files (using the add command) you just copied over to Git so that you can commit them!!

Deadlines
=========

* **7 pm Friday, September 20**. Issue a pull request from your repo to the
  [Assignments repo][] on GitHub. Your pull request must include a _work in
  progress_ that describes at a high level where you are in the
  assignment. This WIP must be detailed enough such that we know your
  status; a description like "Working on Warmup Problems" obviously
  doesn't count.

* **7 pm Wednesday, September 24**. Complete each of the below tasks.

* **7 pm Friday, October 11**. You may reassess or turn in late by this
  date for up to 85%, so long as you complete a valid WIP by 7pm,
  September 24. No credit will otherwise be awarded.
  
Then, assuming you've finished all the tasks, committed all your work to your fork, and filled out some sort of WIP comment/checklist according to what was accomplished on the pull request, you're done. 

Task: Scala koans
=================

In this class, you will be using a number of tools. We will start with
looking at these two first:

* ScalaTest, which supports test-driven development (TDD)

* Scala build tool (sbt), which supports constructing the build steps
  for a project, including test discovery and running these tests

For this task, we will be doing this in the context of the [Scala
koans][]. I recommend going through this site, there are only a few
pages, but there's useful material about working with Scala.

Your repo contains in the koans subdirectory a subset of the Scala
koans. You should find working through these koans to be quite simple
- they just help illustrate Scala language features. But do pay
attention to how the tests are written. Let's look at one koan, from AboutLiteralStrings:

````scala
  koan("Character Literals are quoted with single quotes") {
    val a = 'a'
    val b = 'B'

    a.toString should be(__)
    b.toString should be(__)
  }
````

You will notice the following: assertions with `should be`, test
setup, and even the `koan` DSL. Interesting, `koan` is just a regular
function, but it exploits Scala syntax: it takes *two* parameter
lists, and the second parameter is the block of the test itself. Later
in the course, we will be looking at how to do that ourselves.

Complete the following koans so that all pass successfully:

* AboutValAndVar
* AboutLiteralNumbers
* AboutLiteralStrings
* AboutClasses
* AboutCaseClasses
* AboutObjects
* AboutTuples
* AboutLists
* AboutPatternMatching
* AboutPreconditions


 
Task: Warmup on Scala, `sbt`, and TDD
=====================================

You've been using `sbt` in order to work with the koans. Now you can
do the same for a number of warmup problems. You will also get to
write the corresponding tests. Lucky you!

Project layout
--------------

Here's the project layout we will be using:

~~~~
+---README.md
+---build.sbt
+---src
    +---main
    |---+---scala
    |       +---Warmup.scala
    +---test
        |---scala
            |---WarmupSpec.scala
~~~~

Your README.md should contain a short description of the work
completed.

We have included a generic build.sbt file which should be sufficient
for your purposes.


Testing framework
-----------------

For testing we will be using `FlatSpec` from ScalaTest (and not say
the simpler AtomicTest framework, which also uses ScalaTest). Here's
an example from the documentation of [ScalaTest][]:

````scala
import org.scalatest.FlatSpec import
scala.collection.mutable.Stack

class StackSpec extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}
````

Your tests will generally use assertions like these:

* `x should be(...)`
* `x should equal(...)` or `x === ...`
* `intercept[T]` to ensure an exception of type `T` is thrown

We use  `===` instead of `==` because triple-equals enables better
reporting in the case of failure.

The Testing atom, pp94-99, describes a good approach to TDD.

With this in mind, complete the following problems and corresponding
tests from your Atomic Scala book. Although solutions are provided at
the book website, the caveat for this task is that we also ask you to
write the tests using `FlatSpec` and to use an all-functional style -
no `var` usage in this class!

* Rewrite `calculateBMI` and corresponding tests on pp98-99 to use
  `FlatSpec`.

* More Conditionals, p116, exercises 2 and 3

* Pattern Matching, p131, exercise 2

* Pattern Matching with Case Classes, p190, exercises 1-3

* Lists, p301, exercise 1


Task: Respond to "Beating the Averages"
=======================================

Paul Graham is a noted and highly successful venture capitalist as the
founder of [Y Combinator][]. Before that, he became well-known as a
[blogger][Paul Graham]. His early blogs detailed his even earlier
work as a successful entrepreneur at Viaweb, what became [Yahoo! Small
Business eCommerce Solutions][Yahoo eCommerce]. And before even that,
Paul was known to a much smaller community as the author of a
textbook, [*On Lisp*][On Lisp].

In his essay ["Beating the Averages"][Beating the Averages], Paul
presents his thesis with this argument:

> I'll begin with a shockingly controversial statement: programming
> languages vary in power.

He then goes onto describe what he calls the "Blub Paradox", the idea
that our conception of what can be done is limited by the tools at
hand.

Some of what he states in this essay may not yet be familiar to
you. That's fine, we will discuss macros, among other things, by the
time the course ends. However, you all have programmed, with languages
of varying power as Paul describes it, so I expect you can follow his
argument. I also don't necessarily expect you to agree with this
essay. But that's why I picked it!

In a 300 to 500 word response, written in Markdown, respond to Paul's
blog post. Place your response in [response.md](response.md) in this
directory. Develop a thesis and ground it in your own
experience. Please note that you must contain your essay to this limit; 
we will be checking using the classic Unix program `wc` (word
count). In my experience, I have found constraints to be very helpful,
so I hope you find the same with this aspect of the assignment. Code
samples may certainly be included, but they are not required.

I will end with this quote from the essay:

> During the years we worked on Viaweb I read a lot of job
  descriptions. A new competitor seemed to emerge out of the woodwork
  every month or so. The first thing I would do, after checking to see
  if they had a live online demo, was look at their job
  listings. After a couple years of this I could tell which companies
  to worry about and which not to. The more of an IT flavor the job
  descriptions had, the less dangerous the company was. The safest
  kind were the ones that wanted Oracle experience. You never had to
  worry about those. You were also safe if they said they wanted C++
  or Java developers. If they wanted Perl or Python programmers, that
  would be a bit frightening-- that's starting to sound like a company
  where the technical side, at least, is run by real hackers. If I had
  ever seen a job posting looking for Lisp hackers, I would have been
  really worried.


Markdown
========

All writeups in this class must be written in Markdown. You will also
be using a form of Markdown to describe your work-in-progress on pull
requests.

An example of valid Markup can be seen by looking at this file. This
[reference][Markdown] is a good overview of Markdown; there are many
others. Many tools also support working with Markdown, including
TextEdit. Ask around on Piazza and ask your TAs; I personally use Emacs.

Later on this class you may need to use the pandoc dialect, which
allows for the embedding of Latex markup. GitHub however displays the
Latex markup. Still, this remains generally readable if confined to
math notation - which is what you should do.

Why Markdown? There are a number of good reasons. It starts with the
idea that a markup language like Markdown is ultimately a form of
source code, although in this case strictly for human
communication. Markdown has really good support for talking about the
code we program in. As source code, it can be readily translated to
other formats, whether that's for a presentation or for a paper or
simply an online doc. It's easy to manage with source code control
systems. Although this is true of other markup languages, such as
reStructuredText, Markdown seems to be currently the most popular
option, whether for GitHub or StackOverflow, and it's perhaps the
easiest to master.

<!-- references -->
  [Assignments repo]: https://github.com/csci3155-f13/Assignments
  [Beating the Averages]: http://www.paulgraham.com/avg.html
  [Markdown]: http://daringfireball.net/projects/markdown/syntax
  [On Lisp]: http://www.paulgraham.com/onlisp.html
  [Paul Graham]: http://www.paulgraham.com/
  [Scala koans]: http://scalakoans.webfactional.com/
  [ScalaTest]: http://www.scalatest.org/
  [Y Combinator]: http://ycombinator.com/
  [Yahoo eCommerce]: http://smallbusiness.yahoo.com/ecommerce/
