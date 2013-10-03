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
