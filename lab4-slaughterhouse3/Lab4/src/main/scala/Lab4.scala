object Lab4 {
  import jsy.lab4.ast._
  
  /*
   * CSCI 3155: Lab 4
   */
	//sources: https://github.com/peterklipfel/PL/blob/master/HW4/src/Lab4_pekl2737.scala
	//sources: https://github.com/chadbryant7/csci3155/tree/master/Documents/SPRING%202013/Principles%20of%20Programming/Local%20Repo
  /* Carissa Chen, Bob Werthman, Paul Manis
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
  
  /* Collections and Higher-Order Functions */
  
  /* Lists */
  
  def compressRec[A](l: List[A]): List[A] = l match {
    case Nil | _ :: Nil => l
    case h1 :: (t1 @ (h2 :: _)) => if(h1 == h2) compressRec(t1) else h1::compressRec(t1)
  }
  
  def compressFold[A](l: List[A]): List[A] = l.foldRight(Nil: List[A]){
    (h, acc) => acc match {
		case (h1 :: h2) if(h == h1) => acc
		case _ => h :: acc
    }
  }
  
  def testCompress(compress: List[Int] => List[Int]): Boolean =
    compress(List(1, 2, 2, 3, 3, 3)) == List(1, 2, 3)
	 assert(testCompress(compressRec))
	 assert(testCompress(compressFold))
  
  def mapFirst[A](f: A => Option[A])(l: List[A]): List[A] = l match {
    case Nil => l
    case h :: t => f(h) match {
      case Some(x) => x :: t
      case None => h :: mapFirst(f)(t) 
    }
  }
  
  def testMapFirst(mapFirst: (Int => Option[Int]) => List[Int] => List[Int]): Boolean =
    mapFirst((i: Int) => if (i < 0) Some(-i) else None)(List(1, 2, -3, 4, -5)) == List(1, 2, 3, 4, -5)
   assert(testMapFirst(mapFirst))
  
  /* Trees */
  
  sealed abstract class Tree {
    def insert(n: Int): Tree = this match {
      case Empty => Node(Empty, n, Empty)
      case Node(l, d, r) => if (n < d) Node(l insert n, d, r) else Node(l, d, r insert n)
    } 
    
    def map(f: Int => Int): Tree = this match {
      case Empty => Empty
      case Node(l, d, r) => {
      //do an inorder-traversal simultaneously applying mapping function to vals in nodes
        Node( l.map(f), f(d), r)
        Node( l, f(d), r.map(f))
      }
    }
    
    def foldLeft[A](z: A)(f: (A, Int) => A): A = {
      def loop(acc: A, t: Tree): A = t match {
        case Empty => acc
        case Node(l, d, r) => loop(f(loop(acc,l),d), r)
      }
      loop(z, this)
    }
    
    def pretty: String = {
      def p(acc: String, t: Tree, indent: Int): String = t match {
        case Empty => acc
        case Node(l, d, r) =>
          val spacer = " " * indent
          p("%s%d%n".format(spacer, d) + p(acc, l, indent + 2), r, indent + 2)
      } 
      p("", this, 0)
    }
  }
  case object Empty extends Tree
  case class Node(l: Tree, d: Int, r: Tree) extends Tree
  
  def treeFromList(l: List[Int]): Tree =
    l.foldLeft(Empty: Tree){ (acc, i) => acc insert i }
  
  def incr(t: Tree): Tree = t.map(i => i + 1)
  //def incr(t: SearchTree): SearchTree = t.map{ i => i + 1 }
  //def incr(t: SearchTree): SearchTree = t.map{ _ + 1 } // using placeholder notation
  //println(incr(treeFromList(List(1,2,3))))
  def testIncr(incr: Tree => Tree): Boolean =
    incr(treeFromList(List(1,2,3))) == treeFromList(List(2,3,4))
    assert(testIncr(incr))
  
  def sum(t: Tree): Int = t.foldLeft(0){ (acc, d) => acc + d }
  //println(sum(treeFromList(List(1,2,3))))
  def testSum(sum: Tree => Int): Boolean =
    sum(treeFromList(List(1,2,3))) == 6
    assert(testSum(sum))

  def strictlyOrdered(t: Tree): Boolean = {
    val (b, _) = t.foldLeft((true, None: Option[Int])){
      (b, d) => if (b._1) {
        b._2 match {
          case None => (true, Some(d))
          case Some(x) => ( d > x, Some(d))
        }
      }
      else ( false, None)
    }
    b
  }
  //println(strictlyOrdered(treeFromList(List(1,1,2))))
  def testStrictlyOrdered(strictlyOrdered: Tree => Boolean): Boolean =
    !strictlyOrdered(treeFromList(List(1,1,2)))
  assert(testStrictlyOrdered(strictlyOrdered))
  
  /* Type Inference */
  
  def hasFunctionTyp(t: Typ): Boolean = t match {
	case TFunction(p, r) => true
    case _ => false
  }
  
  def typeInfer(env: Map[String,Typ], e: Expr): Typ = {
    def typ(e1: Expr) = typeInfer(env, e1)
    def err[T](tgot: Typ, e1: Expr): T = throw new StaticTypeError(tgot, e1, e)
    e match {
      case Print(e1) => typ(e1); TUndefined
      case N(_) => TNumber
      case B(_) => TBool
      case Undefined => TUndefined
      case S(_) => TString
      case Var(x) => env(x)
      case ConstDecl(x, e1, e2) => typeInfer(env + (x -> typ(e1)), e2)
      case Unary(Neg, e1) => typ(e1) match {
        case TNumber => TNumber
        case tgot => err(tgot, e1)
      }
      
      case Unary(Not, e1) => typ(e1) match {
		case TBool => TBool
		case tgot => err(tgot, e1)
	  }
	  
	   case Binary(Plus, e1, e2) => (typ(e1), typ(e2)) match {
		case (TString, TString) => TString
    case (TNumber, TNumber) => TNumber
		case _ => err(typ(e1), e1)
	  }
	    
	  case Binary(And, e1, e2) => (typ(e1), typ(e2)) match {
		case (TBool, TBool) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case Binary(Or, e1, e2) => (typ(e1), typ(e2)) match {
		case (TBool, TBool) => TBool
		case _ => err(typ(e1), e1)
	  }
	  case Binary(Eq, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case (TBool, TBool) => TBool
		case (TObj(x1), TObj(x2)) => TBool
		case (TUndefined, TUndefined) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case Binary(Ne, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case (TBool, TBool) => TBool
		case (TObj(x1), TObj(x2)) => TBool
		case (TUndefined, TUndefined) => TBool
		case _ => err(typ(e1), e1)
	  }
	  case Binary(Lt, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case Binary(Le, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case Binary(Gt, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case Binary(Ge, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TBool
		case (TString, TString) => TBool
		case _ => err(typ(e1), e1)
	  }
	  
	  case If(p, e1, e2) => (typ(p), typ(e1), typ(e2)) match {
		case (TBool, x, y) =>  {
			if (x == y) x
			else err(y, e2) 
			}
			case(a, _, _) => err(a, p)
	  }	  
	  
	   case Binary(Seq, e1, e2) => (typ(e1), typ(e2)) match {
      case _ => typ(e2)
     }
	  
	  case Binary(op, e1, e2) => (typ(e1), typ(e2)) match {
		case (TNumber, TNumber) => TNumber
		case (tgot1, tgot2) => err(tgot1, e1)
	  }
	  
      case Call(f, x) => {
        val typef = typ(f)
        typef match {
          case TFunction(p, r) => {
            for ((tx: ((String, Typ), Expr)) <- p.zip(x)){
              if (tx._1._2 != typ(tx._2)) err(typ(tx._2), tx._2)
            }
            if (p.length == x.length) r
            else err(typef, f)
          }
          case _ => err(typef, f)
        } 
      }
         
      case Obj(field) => TObj(field.mapValues((exp: Expr) => typ(exp)))
      case GetField(obj, f) => {
        typ(obj) match {
          case TObj(field) => field.get(f) match {
            case Some(f) => f
            case None => err(typ(obj), obj)
          }
          case _ => err(typ(obj), obj)
        }
      }  
	
      case Function(p, params, tann, e1) => {
        // Bind to env1 an environment that extends env with an appropriate binding if
        // the function is potentially recursive.
        val env1 = (p, tann) match {
          case (Some(f), Some(rt)) =>
            val tprime = TFunction(params, rt)
            env + (f -> tprime)
          case (None, _) => env
          case _ => err(TUndefined, e1)
        }
        // Bind to env2 an environment that extends env1 with bindings for params.
        val env2 = env1 ++ params
        // Match on whether the return type is specified.
        tann match {
          case None => TFunction(params, typeInfer(env2, e1))
          case Some(rt) => {  if (rt == typeInfer(env2, e1))
              TFunction(params, rt)
            else
              TFunction(params, err(rt, e1))
			}
        }
      }
      case _ => throw new UnsupportedOperationException
    }
  
  }
  def inferType(e: Expr): Typ = typeInfer(Map.empty, e)
  
  /* Small-Step Interpreter */
  
  def inequalityVal(bop: Bop, v1: Expr, v2: Expr): Boolean = {
    require(bop == Lt || bop == Le || bop == Gt || bop == Ge)
    ((v1, v2): @unchecked) match {
      case (S(s1), S(s2)) =>
        (bop: @unchecked) match {
          case Lt => s1 < s2
          case Le => s1 <= s2
          case Gt => s1 > s2
          case Ge => s1 >= s2
        }
      case (N(n1), N(n2)) =>
        (bop: @unchecked) match {
          case Lt => n1 < n2
          case Le => n1 <= n2
          case Gt => n1 > n2
          case Ge => n1 >= n2
        }
    }
  }
  
  def substitute(e: Expr, v: Expr, x: String): Expr = {
    require(isValue(v))
    
    def subst(e: Expr): Expr = substitute(e, v, x)
    /*
    e match {
      case N(_) | B(_) | Undefined | S(_) => e
      case Print(e1) => Print(subst(e1))
      case Unary(uop, e1) => Unary(uop, subst(e1))
      case Binary(bop, e1, e2) => Binary(bop, subst(e1), subst(e2))
      case If(e1, e2, e3) => If(subst(e1), subst(e2), subst(e3))
      case Call(e1, args) => Call(subst(e1), args map subst)
      case Var(y) => if (x == y) v else e
      case ConstDecl(y, e1, e2) =>
        ConstDecl(y, subst(e1), if (x == y) e2 else subst(e2))
        case GetField(e1, f) => GetField(subst(e1), f)
      case Obj(params) => Obj(params.mapValues(v => subst(v)))
      //case Call(e1, args) => Call(subst(e1), args map subst)
      case Function(p, params, tann, e1) => Function(p, params, tann, subst(e1))
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def step(e: Expr): Expr = {
    require(!isValue(e))
    
    def stepIfNotValue(e: Expr): Option[Expr] = if (isValue(e)) None else Some(step(e))
    
    e match {
      /* Base Cases: Do Rules */
      case Print(v1) if isValue(v1) => println(pretty(v1)); Undefined
      case Unary(Neg, N(n1)) => N(- n1)
      case Unary(Not, B(b1)) => B(! b1)
      case Binary(Seq, v1, e2) if isValue(v1) => e2
      case Binary(Plus, S(s1), S(s2)) => S(s1 + s2)
      case Binary(Plus, N(n1), N(n2)) => N(n1 + n2)
      case Binary(bop @ (Lt|Le|Gt|Ge), v1, v2) if isValue(v1) && isValue(v2) => B(inequalityVal(bop, v1, v2))
      case Binary(Eq, v1, v2) if isValue(v1) && isValue(v2) => B(v1 == v2)
      case Binary(Ne, v1, v2) if isValue(v1) && isValue(v2) => B(v1 != v2)
      case Binary(And, B(b1), e2) => if (b1) e2 else B(false)
      case Binary(Or, B(b1), e2) => if (b1) B(true) else e2
      case ConstDecl(x, v1, e2) if isValue(v1) => substitute(e2, v1, x)
      /*** Fill-in more cases here. ***/
      case Binary(Minus, N(n1), N(n2)) => N(n1-n2)
      case Binary(Times, N(n1), N(n2)) => N(n1*n2)
      case Binary(Div, N(n1), N(n2)) => N(n1/n2)
      case If(B(true), e2, e3) => step(e2)
      case If(B(false), e2, e3) => step(e3)
      case GetField(Obj(fields), f) => fields.get(f) match {
        case Some(e) => e
        case None => throw new StuckError(e)
      }
      case Call(Function(None, params, _, e1), args) if (args.foldLeft(true)((truth, x) => truth && isValue(x))) => {
        val zippedList = params zip args
        zippedList.foldLeft(e1) { (express, x) =>
          x match {
            case ((name, _), value) => substitute(express, value, name)
          }
        }
      }
      case Call(f1 @ Function(Some(f), params, _, e1), args) if (args.foldLeft(true)((truth, x) => truth && isValue(x))) => {
        val zippedList = params zip args
        zippedList.foldLeft(e1) { (express, x) =>
          x match {
            case ((name, _), value) => substitute(express, value, name); substitute(express, f1, f)
          }
        }
      }

      /*
      case Call((func @ Function(p, params, _, bod)), args) if args.forall(isValue) => {
          val bp = p match {
            case Some(f) => substitute(bod, func, f)
            case None => bod
          }
        params.zip(args).foldLeft(bp){
         (e1: Expr, t1: ((String, Typ), Expr)) => substitute(e1, t1._2, t1._1._1)
        }
      }
      */
      /*
      case Call( v @ Function(p, params, tann ,e1), args) if args.forall(isValue(x)) => {
        v match {
          case Function(None, params, tann, e1) => params.zip(args).foldRight(None){ substitute(e1, , params.foldRight(0)())
          case Function(Some(f), params, tann ,e1)
        }
      }
      */
      /* Inductive Cases: Search Rules */
      case Print(e1) => Print(step(e1))
      case Unary(uop, e1) => Unary(uop, step(e1))
      case Binary(bop, v1, e2) if isValue(v1) => Binary(bop, v1, step(e2))
      case Binary(bop, e1, e2) => Binary(bop, step(e1), e2)
      case If(e1, e2, e3) => If(step(e1), e2, e3)
      case ConstDecl(x, e1, e2) => ConstDecl(x, step(e1), e2)
      /*** Fill-in more cases here. ***/
      case Obj(fields) => Obj(fields.mapValues((exp: Expr) => step(exp)))
      case GetField(e1, f) => GetField(step(e1), f)
      //case Obj(fields) => fields.find { case (a,b) => !isValue(b) }
      //case Call(Function(p, params, tann, e1), args) => Call(Function(p, params, tann, e1), mapFirst((arg: Expr) => stepIfNotValue(arg))(args)) 
      case Call(v1 @ Function(_, _, _, _), args) => {
        val args1 = mapFirst { (a: Expr) => if (!isValue(a)) Some(step(a)) else None }(args)
        Call(v1, args1)
      }
      case Call(e1, args) => Call(step(e1), args)

      /* Everything else is a stuck error. */
      case _ => throw new StuckError(e)
    }
  }

  def iterateStep(e: Expr): Expr =
    if (isValue(e)) e else iterateStep(step(e))
    
}*/
/*
e match {
      case N(_) | B(_) | Undefined | S(_) => e
      case Print(e1) => Print(subst(e1))
      case Unary(uop, e1) => Unary(uop, subst(e1))
      case Binary(bop, e1, e2) => Binary(bop, subst(e1), subst(e2))
      case If(e1, e2, e3) => If(subst(e1), subst(e2), subst(e3))
      case Var(y) => if (x == y) v else e
      case ConstDecl(y, e1, e2) => ConstDecl(y, subst(e1), if (x == y) e2 else subst(e2))
      case GetField(e1, f) => GetField(subst(e1), f)
      case Obj(params) => Obj(params.mapValues(v => subst(v)))
      case Call(e1, args) => Call(subst(e1), args map subst)
      case f1 @ Function(p, params, tann, e1) => {
        if (params.exists((t1: (String, Typ)) => t1._1 == x) || Some(x) == p) {
          f1
        } else {
          Function(p, params, tann, subst(e1))
        }
      }
      case _ => println("the following fields were not found"); println(e); throw new UnsupportedOperationException
    }
  }

  def step(e: Expr): Expr = {
    require(!isValue(e))

    def stepIfNotValue(e: Expr): Option[Expr] = if (isValue(e)) None else Some(step(e))

    e match {
      /* Base Cases: Do Rules */
      case Print(v1) if isValue(v1) => println(pretty(v1)); Undefined
      case Unary(Neg, N(n1)) => N(-n1)
      case Unary(Not, B(b1)) => B(!b1)
      case Binary(Seq, v1, e2) if isValue(v1) => e2
      case Binary(Plus, S(s1), S(s2)) => S(s1 + s2)
      case Binary(Plus, N(n1), N(n2)) => N(n1 + n2)
      case Binary(Minus, N(n1), N(n2)) => N(n1 - n2)
      case Binary(Times, N(n1), N(n2)) => N(n1 * n2)
      case Binary(Div, N(n1), N(n2)) => N(n1 / n2)
      case Binary(bop @ (Lt | Le | Gt | Ge), v1, v2) if isValue(v1) && isValue(v2) => B(inequalityVal(bop, v1, v2))
      case Binary(Eq, v1, v2) if isValue(v1) && isValue(v2) => B(v1 == v2)
      case Binary(Ne, v1, v2) if isValue(v1) && isValue(v2) => B(v1 != v2)
      case Binary(And, B(b1), e2) => if (b1) e2 else B(false)
      case Binary(Or, B(b1), e2) => if (b1) B(true) else e2
      case ConstDecl(x, v1, e2) if isValue(v1) => substitute(e2, v1, x)
      /*** Fill-in more cases here. ***/
      case If(B(true), e2, e3) => e2
      case If(B(false), e2, e3) => e3
      case GetField(Obj(fields), f) if (fields.forall {
        case (_, vi) => isValue(vi)
      }) => fields.get(f) match {
        case None => throw new StuckError(e)
        case Some(v) => v
      }
      case Call(Function(None, params, _, e1), args) if (args.foldLeft(true)((truth, x) => truth && isValue(x))) => {
        val zippedList = params zip args
        zippedList.foldLeft(e1) { (express, x) =>
          x match {
            case ((name, _), value) => substitute(express, value, name)
          }
        }
      }
      case Call(f1 @ Function(Some(f), params, _, e1), args) if (args.foldLeft(true)((truth, x) => truth && isValue(x))) => {
        val zippedList = params zip args
        zippedList.foldLeft(e1) { (express, x) =>
          x match {
            case ((name, _), value) => substitute(express, value, name); substitute(e1, f1, f)
          }
        }
      }

      /* Inductive Cases: Search Rules */
      case Print(e1) => Print(step(e1))
      case Unary(uop, e1) => Unary(uop, step(e1))
      case Binary(bop, v1, e2) if isValue(v1) => Binary(bop, v1, step(e2))
      case Binary(bop, e1, e2) => Binary(bop, step(e1), e2)
      case If(e1, e2, e3) => If(step(e1), e2, e3)
      case ConstDecl(x, e1, e2) => ConstDecl(x, step(e1), e2)
      /*** Fill-in more cases here. ***/
      case Call(v1 @ Function(_, _, _, _), args) => {
        val args1 = mapFirst { (a: Expr) => if (!isValue(a)) Some(step(a)) else None }(args)
        Call(v1, args1)
      }
      case Call(e1, args) => Call(step(e1), args)
      case GetField(e1, f) => GetField(step(e1), f)
      case Obj(f) => {
        val fList = f.toList
        def newFunction(arg: (String, Expr)): Option[(String, Expr)] = {
          arg match {
            case (s, e1) => if (!isValue(e1)) Some(s, step(e1)) else None
          }
        }
        val newList = mapFirst(newFunction)(fList)
        val fMap = newList.toMap
        Obj(fMap)
      }

      /* Everything else is a stuck error. */
      case _ => println(e); throw new StuckError(e)
    }
  }

  def iterateStep(e: Expr): Expr =
    if (isValue(e)) e else iterateStep(step(e))

}*/

e match {
      case N(_) | B(_) | Undefined | S(_) => e
      case Print(e1) => Print(subst(e1))
      case Unary(uop, e1) => Unary(uop, subst(e1))
      case Binary(bop, e1, e2) => Binary(bop, subst(e1), subst(e2))
      case If(e1, e2, e3) => If(subst(e1), subst(e2), subst(e3))
      case Call(e1, args) => Call(subst(e1), args map subst)
      case Var(y) => if (x == y) v else e
      case ConstDecl(y, e1, e2) =>
        ConstDecl(y, subst(e1), if (x == y) e2 else subst(e2))
      case Obj(field) => Obj(field.mapValues((exp: Expr) => subst(exp)))
      case GetField(e1, f) => if (x != f) GetField(subst(e1), f) else e
      case f1 @ Function(p, params, tann, e1) => {
        if (params.exists((t1: (String, Typ)) => t1._1 == x) || Some(x) == p) {
          f1
        } else {
          Function(p, params, tann, subst(e1))
        }
      }
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def step(e: Expr): Expr = {
    require(!isValue(e))
    
    def stepIfNotValue(e: Expr): Option[Expr] = if (isValue(e)) None else Some(step(e))
    
    e match {
      /* Base Cases: Do Rules */
      case Print(v1) if isValue(v1) => println(pretty(v1)); Undefined
      case Unary(Neg, N(n1)) => N(- n1)
      case Unary(Not, B(b1)) => B(! b1)
      case Binary(Seq, v1, e2) if isValue(v1) => e2
      case Binary(Plus, S(s1), S(s2)) => S(s1 + s2)
      case Binary(Plus, N(n1), N(n2)) => N(n1 + n2)
      case Binary(bop @ (Lt|Le|Gt|Ge), v1, v2) if isValue(v1) && isValue(v2) => B(inequalityVal(bop, v1, v2))
      case Binary(Eq, v1, v2) if isValue(v1) && isValue(v2) => B(v1 == v2)
      case Binary(Ne, v1, v2) if isValue(v1) && isValue(v2) => B(v1 != v2)
      case Binary(And, B(b1), e2) => if (b1) e2 else B(false)
      case Binary(Or, B(b1), e2) => if (b1) B(true) else e2
      case ConstDecl(x, v1, e2) if isValue(v1) => substitute(e2, v1, x)
      /*** Fill-in more cases here. ***/
      case Binary(Minus, N(n1), N(n2)) => N(n1 - n2)
      case Binary(Times, N(n1), N(n2)) => N(n1 * n2)
      case Binary(Div, N(n1), N(n2)) => N(n1 / n2)
      /* Inductive Cases: Search Rules */
      case Print(e1) => Print(step(e1))
      case Unary(uop, e1) => Unary(uop, step(e1))
      case Binary(bop, v1, e2) if isValue(v1) => Binary(bop, v1, step(e2))
      case Binary(bop, e1, e2) => Binary(bop, step(e1), e2)
      case If(B(true), e2, e3) => e2
      case If(B(false), e2, e3) => e3
      case If(e1, e2, e3) => If(step(e1), e2, e3)
      case ConstDecl(x, e1, e2) => ConstDecl(x, step(e1), e2)
      /*** Fill-in more cases here. ***/
      
      case Call((func @ Function(p, params, _, bod)), args) if args.forall(isValue) => {
          val bp = p match {
            case Some(f) => substitute(bod, func, f)
            case None => bod
          }
        params.zip(args).foldLeft(bp){
         (e1: Expr, t1: ((String, Typ), Expr)) => substitute(e1, t1._2, t1._1._1)
        }
      }

      case Call(Function(p, params, tann, bod), args) => {
        Call(Function(p, params, tann, bod), mapFirst(
            (arg: Expr) => if (isValue(arg)) { 
               None
              } 
              else {
               Some(step(arg))
              }
          )(args))
      }
      

      case Call(e1, args) => Call(step(e1), args)

      case Obj(fields) => {
        Obj(fields./:((false, Map[String, Expr]())){
            (exist: (Boolean, Map[String, Expr]), current: (String, Expr)) =>
              if (!exist._1 && !isValue(current._2)) (true, exist._2 + (current._1 -> step(current._2)))
              else (exist._1, exist._2 + (current._1 -> current._2))
          }._2
        )
      }

      case GetField(Obj(fields), f) => fields.get(f) match {
        case Some(e) => e 
        case None => throw new StuckError(e)
      }
      case GetField(e1, f) => GetField(step(e1), f)

      /* Everything else is a stuck error. */
      case _ => throw new StuckError(e)
    }
  }

  def iterateStep(e: Expr): Expr =
    if (isValue(e)) e else iterateStep(step(e))
    
}
