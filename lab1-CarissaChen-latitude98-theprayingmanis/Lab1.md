Scala Basics: Binding and Scope
===============================
 1. The use of `pi` on line #4 is bound at line #3
 2. The use of `pi` on line #7 is bound at line #1
 3. The use of `x` on line #3 is bound at line #2
 4. The use of `x` on line #6 is bound at line #2
 5. The use of `x` on line #10 is bound at line #5
 6. The use of `x` on line #13 is bound at line #1

Scala Basics: Types
=======================
 **Yes**, the body of `g` is well-typed with type `((Int,Int),Int)`:
 1) `(b,a): ((Int,Int),Int)` because a) `b: (int,int)` b) `a: Int`


`sqrtN` Recursive vs. `sqrtN` with `while` loop
==============================================
 `sqrtN` with `while` loop
 ````scala
 def sqrtN(c: Double, x0: Double, n: Int): Double = {
    if (n < 0) { throw new IllegalArgumentException }
    else if (x0 < 0) { throw new IllegalArgumentException }
    else if (n == 0) {return x0}
    else { 
      //sqrtN(c, sqrtStep(c,x0), n-1)
      var p = n
      var xp = x0
      while ( p > 0){
        p -= 1
        if (xp < 0) { throw new IllegalArgumentException }
        else {xp = xp - ((xp*xp)-c)/(2*xp)}
      }
      xp
    }
  } 	
 ````
 As can be seen by the code above, using a `while` loop requires, at least in my case more code than the recursive version.  Also you have to declare a few `var` variables in order for the loop to work.  I feel the code is harder to follow than the recursive version and doesn't look very concise.  But that also could be attributed to my coding style.
`sqrtErr` recursive vs. `sqrtErr`with `while` loop
====================================================
````scala
def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = {
    if (x0 < 0) { throw new IllegalArgumentException }
    else if (epsilon <= 0) { throw new IllegalArgumentException }
    //else if (abs((x0*x0)-c) < epsilon) {return x0}
    else  {
     //sqrtErr(c, sqrtStep(c, x0), epsilon)
     var xp = x0
     var cp = c
     var ep = epsilon
     while (abs((xp*xp)-cp) > ep){
      if (xp < 0) { throw new IllegalArgumentException }
      else { xp = xp - ((xp*xp)- cp)/(2*xp) }
     }
     xp
      }
  }
````
Again, as can be seen by the code, the `while` loop takes more code than the recursive version.  And some `var` variables needed to be declared in order for the `while` loop to work.  It is not very clear and hard to follow in my opinion.  And I don't think it is as intuitive as the recursive version.

Functional Approach of `insert`
===============================
This functional approach of `insert` is required for case classes because the method `insert` accesses each node individually.  It's not like **C++** where can access a successor node and its data with a pointer before going to that node. You have to actually travel to each node with the method `insert`.  Hypothetically, the rest of the tree doesn't exist when looking at one node therefore you can't destructively update the intput tree.