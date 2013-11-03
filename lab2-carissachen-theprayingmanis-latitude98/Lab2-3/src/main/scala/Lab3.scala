object Lab3 {
  import jsy.lab3.ast._
  
  /*
   * CSCI 3155: Lab 3 
   * Robert Werthman
   * 
   * Partner: Carissa Tara Chen, Paul Manis
   * Collaborators: <Any Collaborators>
   */

  /*
   * Fill in the appropriate portions above by replacing things delimited
   * by '<'... '>'.
   * 
   * Replace the 'throw new UnsupportedOperationException' expression with
   * your code in each function.
   * 
   * Do not make other modifications to this template, such as
   * - adding "extends App" or "extends Application" to your Lab object,
   * - adding a "main" method, and
   * - leaving any failing asserts.
   * 
   * Your lab will not be graded if it does not compile.
   * 
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * 'throws new UnsupportedOperationException' as needed to get something
   * that compiles without error.
   */
  
  type Env = Map[String, Expr]
  val emp: Env = Map()
  def get(env: Env, x: String): Expr = env(x)
  // "extend" environment env with the mapping (x -> v),
  // i.e. now variable x is mapped to value v
  def extend(env: Env, x: String, v: Expr): Env = {
    require(isValue(v))
    env + (x -> v)
  }
  
  def toNumber(v: Expr): Double = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => n.toDouble // TODO - implement this case
      case B(false) => 0.0 // TODO - implement this case
      case B(true) => 1.0 // TODO - implement this case
      case Undefined => Double.NaN
      //case S(s) => try s.toDouble catch { case _ => Double.NaN }
      case Function(_, _, _) => Double.NaN
    }
  }
  
  def toBoolean(v: Expr): Boolean = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => if (n == 0 || n == Double.NaN) false else true // TODO - implement this case
      case B(b) => b // TODO - implement this case
      case Undefined => false
      case S("") => false // TODO - implement this case
      case S(_) => true // TODO - implement this case
      case Function(_, _, _) => true
    }
  }
  
  def toString(v: Expr): String = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => n.toString
      case B(b) => b.toString
      case Undefined => "undefined"
      case S(s) => s
      case Function(_, _, _) => "function"
    }
  }
  
  /* Big-Step Interpreter with Dynamic Scoping */
  
  /*
   * This code is a reference implementation of JavaScripty without
   * strings and functions (i.e., Lab 2).  You are to welcome to
   * replace it with your code from Lab 2.
   */
  def eval(env: Env, e: Expr): Expr = {
    def eToN(e: Expr): Double = toNumber(eval(env, e))
    def eToB(e: Expr): Boolean = toBoolean(eval(env, e))
    def eToVal(e: Expr): Expr = eval(env, e)
    e match {
      /* Base Cases */
      case _ if (isValue(e)) => e
      case Var(x) => get(env, x)
      
      /* Inductive Cases */
      case Print(e1) => println(pretty(eval(env, e1))); Undefined
      
      case Unary(Neg, e1) => N(- eToN(e1))
      case Unary(Not, e1) => B(! eToB(e1))

      case Binary(Plus, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => S(s1 + toString(v2)) // TODO - implement this case
        case (v1, S(s2)) => S(toString(v1) + s2) // TODO - implement this case
        case (v1, v2) => N(eToN(v1) + eToN(v2)) // TODO - implement this case
      } 
        
      case Binary(Minus, e1, e2) => N(eToN(e1) - eToN(e2))
      case Binary(Times, e1, e2) => N(eToN(e1) * eToN(e2)) // TODO - implement this case
      case Binary(Div, e1, e2) => N(eToN(e1) / eToN(e2)) // TODO - implement this case
      
      case Binary(Eq, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (Function(_,_,_), _) => throw new DynamicTypeError(e)
        case (_, Function(_,_,_)) => throw new DynamicTypeError(e)
        case (S(s1), S(s2)) => B(s1 == s2)
        case (N(n1), N(n2)) => B(n1 == n2)
        case (B(b1), B(b2)) => B(b1 == b2)
        case (_, _) => B(false)
      }
      case Binary(Ne, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (Function(_,_,_), _) => throw new DynamicTypeError(e)
        case (_, Function(_,_,_)) => throw new DynamicTypeError(e)
        case (S(s1), S(s2)) => B(s1 != s2) // TODO - implement this case
        case (N(n1), N(n2)) => B(n1 != n2) // TODO - implement this case
        case (B(b1), B(b2)) => B(b1 != b2) // TODO - implement this case
        case (_, _) => B(true)
      }    
  
      case Binary(Lt, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => B(s1 < toString(v2))
        case (v1, S(s2)) => B(toString(v1) < s2)
        case (v1, v2) => B(toNumber(v1) < toNumber(v2)) 
      } 
      case Binary(Le, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => B(s1 <= toString(v2)) // TODO - implement this case
        case (v1, S(s2)) => B(toString(v1) <= s2) // TODO - implement this case
        case (v1, v2) => B(toNumber(v1) <= toNumber(v2)) // TODO - implement this case 
      } 
      case Binary(Gt, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => B(s1 > toString(v2))
        case (v1, S(s2)) => B(toString(v1) > s2)
        case (v1, v2) => B(toNumber(v1) > toNumber(v2)) 
      } 
      case Binary(Ge, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => B(s1 >= toString(v2))
        case (v1, S(s2)) => B(toString(v1) >= s2)
        case (v1, v2) => B(toNumber(v1) >= toNumber(v2)) 
      } 
      
      case Binary(And, e1, e2) => if (eToB(e1)) eToVal(e2) else B(false) // TODO - implement this case 
      case Binary(Or, e1, e2) => if (eToB(e1)) B(true) else eToVal(e2)
      
      case Binary(Seq, e1, e2) => eToVal(e1); eToVal(e2)
      
      case If(e1, e2, e3) => if (eToB(e1)) eToVal(e2) else eToVal(e3) // TODO - implement this case
      
      case ConstDecl(x, e1, e2) => eval(extend(env, x, eToVal(e1)), e2)
      
      case Call(e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (Function(None, x, ebody), v2) => eval(extend(env, x, v2), ebody) // TODO - implement this case
        case (v1 @ Function(Some(p), x, ebody), v2) => eval(extend(extend(env, x, v2), p, v1), ebody) // TODO - implement this case
        case (_, _) => throw new DynamicTypeError(e) 
      }
 
      case _ => throw new UnsupportedOperationException
    }
  }
    
  def evaluate(e: Expr): Expr = eval(emp, e)
  
  
  /* Small-Step Interpreter with Static Scoping */
  
  def substitute(e: Expr, v: Expr, x: String): Expr = {
    require(isValue(v))
    /* Simple helper that calls substitute on an expression
     * with the input value v and variable name x. */
    def subst(e: Expr): Expr = substitute(e, v, x)
    /* Body */
    e match {
      case N(_) | B(_) | Undefined | S(_) => e
      case Function(p, y, ebody) => p match { 
        case Some(f) => {
          if (x == f) e 
          else {
            if (x == y) e else Function(p, y, subst(ebody))
          }
        } 
        case None => if (x == y) e else Function(None, y, subst(ebody))
      }
      case Print(e1) => Print(subst(e1)) // TODO - implement this case
      case Binary(bop, e1, e2) => Binary(bop, subst(e1), subst(e2))
      case Unary(uop, e1) => Unary(uop, subst(e1))
      case If(e1, e2, e3) => If(subst(e1), subst(e2), subst(e3)) // TODO - implement this case
      case ConstDecl(y, e1, e2) => if (x == y) ConstDecl(y, subst(e1), e2) else ConstDecl(y, subst(e1), subst(e2))
      case Call(e1, e2) => Call(subst(e1), subst(e2)) // TODO - implement this case
      case Var(y) => if (y == x) v else Var(y) // TODO - implement this case
      case _ => throw new UnsupportedOperationException
    }
  }
    
  def step(e: Expr): Expr = {
    require(!isValue(e))
    e match {
      /* Base Cases: Do Rules */
      case Print(v1) if (isValue(v1)) => println(pretty(v1)); Undefined

      case Unary(Neg, v1) if (isValue(v1)) => N(-toNumber(v1))
      case Unary(Not, v1) if (isValue(v1)) => B(!toBoolean(v1))

      case Binary(Seq, v1, e2) if (isValue(v1)) => e2

      case Binary(Plus, S(s1), v2) if (isValue(v2)) => S(s1 + toString(v2))
      case Binary(Plus, v1, S(s2)) if (isValue(v1)) => S(toString(v1) + s2)
      case Binary(bop @ (Plus|Minus|Times|Div), v1, v2) if (isValue(v1) && isValue(v2)) => bop match {
        case Plus => N(toNumber(v1) + toNumber(v2))
        case Minus => N(toNumber(v1) - toNumber(v2)) // TODO - implement this case
        case Times => N(toNumber(v1) * toNumber(v2)) // TODO - implement this case
        case Div => N(toNumber(v1) / toNumber(v2)) // TODO - implement this case
        case _ => throw new UnsupportedOperationException 
      }
 
      case Binary(bop @ (Lt|Le|Gt|Ge), S(s1), v2) if (isValue(v2)) => bop match {
        case Lt => B(s1 < toString(v2))
        case Le => B(s1 <= toString(v2))
        case Gt => B(s1 > toString(v2))
        case Ge => B(s1 >= toString(v2))
        case _ => throw new UnsupportedOperationException 
      }
      case Binary(bop @ (Lt|Le|Gt|Ge), v1, S(s2)) if (isValue(v1)) => bop match {
        case Lt => B(toString(v1) < s2) // TODO - implement this case
        case Le => B(toString(v1) <= s2) // TODO - implement this case
        case Gt => B(toString(v1) > s2) // TODO - implement this case
        case Ge => B(toString(v1) >= s2) // TODO - implement this case
        case _ => throw new UnsupportedOperationException 
      }
      case Binary(bop @ (Lt|Le|Gt|Ge), v1, v2) if (isValue(v1) && isValue(v2)) => bop match {
        case Lt => B(toNumber(v1) < toNumber(v2))
        case Le => B(toNumber(v1) <= toNumber(v2))
        case Gt => B(toNumber(v1) > toNumber(v2))
        case Ge => B(toNumber(v1) >= toNumber(v2))
        case _ => throw new UnsupportedOperationException 
      }

      case Binary(bop @ (Eq|Ne), Function(_,_,_), v2) if (isValue(v2)) => throw new DynamicTypeError(e)
      case Binary(bop @ (Eq|Ne), v1, Function(_,_,_)) if (isValue(v1)) => throw new DynamicTypeError(e)
      case Binary(bop @ (Eq|Ne), v1, v2) if (isValue(v1) && isValue(v2)) => bop match {
        case Eq => B(toNumber(v1) == toNumber(v2))
        case Ne => B(toNumber(v1) != toNumber(v2))
        case _ => throw new UnsupportedOperationException 
      }

      case Binary(And, B(true), e2) => e2 // TODO - implement this case
      case Binary(And, B(false), e2) => B(false) // TODO - implement this case
      case Binary(Or, B(true), e2) => B(true) // TODO - implement this case
      case Binary(Or, B(false), e2) => e2
 
      case If(B(true), e2, e3) => e2 // TODO - implement this case
      case If(B(false), e2, e3) => e3 // TODO - implement this case

      case ConstDecl(x, v1, e2) if (isValue(v1)) => substitute(e2, v1, x)
      case Call(v1, v2) if (isValue(v1) && isValue(v2)) => v1 match {
        case Function(None, x, ebody) => substitute(ebody, v2, x) // TODO - implement this case
        case Function(Some(f), x, ebody) => substitute(substitute(ebody, v1, f), v2, x)  // TODO - implement this case
        case _ => throw new DynamicTypeError(e)
      }
      /* Inductive Cases: Search Rules */
      case Print(e1) => Print(step(e1)) // TODO - implement this case
      
      case Unary(uop, e1) => Unary(uop, step(e1))

      case Binary(bop @ (Eq|Ne), Function(_,_,_), e2) => throw new DynamicTypeError(e)
      case Binary(bop, v1, e2) if (isValue(v1)) => Binary(bop, v1, step(e2))
      case Binary(bop, e1, e2) => Binary(bop, step(e1), e2)

      case If(e1, e2, e3) => If(step(e1), e2, e3) // TODO - implement this case

      case ConstDecl(x, e1, e2) => ConstDecl(x, step(e1), e2)

      case Call(v1 @ Function(_,_,_), e2) => Call(v1, step(e2)) // TODO - implement this case
      case Call(v1, e2) if (isValue(v1)) => throw new DynamicTypeError(e)
      case Call(e1, e2) => Call(step(e1), e2) // TODO - implement this case
 
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def iterateStep(e: Expr): Expr =
    if (isValue(e)) e else iterateStep(step(e))
    
}
