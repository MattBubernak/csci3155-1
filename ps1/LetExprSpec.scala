import org.scalatest._
import LetExpr._

class LetExprSpec extends FlatSpec {

  "parser" should "correctly parse simple expressions" in {
    assert(ExprParser.parse("1+2") === BinOp("+", Num(1.0), Num(2.0)))
    assert(ExprParser.parse("1 + 2") === BinOp("+", Num(1.0), Num(2.0)))
    assert(ExprParser.parse("1 * 2") === BinOp("*", Num(1.0), Num(2.0)))
    assert(ExprParser.parse("1 / 2") === BinOp("/", Num(1.0), Num(2.0)))
    assert(ExprParser.parse("1 - 2") === BinOp("-", Num(1.0), Num(2.0)))
    assert(ExprParser.parse("3 * 4") === BinOp("*", Num(3.0), Num(4.0)))
    
  }
  
  "parser" should "correctly parse left to right" in {
    assert(ExprParser.parse("1 * 2 * 3") === BinOp("*", BinOp("*", Num(1.0), Num(2.0)), Num(3.0)))
    assert(ExprParser.parse("1 - 2 - 3") === BinOp("-", BinOp("-", Num(1.0), Num(2.0)), Num(3.0)))
    // add additional tests
    assert(ExprParser.parse("1 / 2 / 3") === BinOp("/", BinOp("/", Num(1.0), Num(2.0)), Num(3.0)))
    assert(ExprParser.parse("1 + 2 + 3") === BinOp("+", BinOp("+", Num(1.0), Num(2.0)), Num(3.0)))
    assert(ExprParser.parse("5 - 4 - 3") === BinOp("-", BinOp("-", Num(5.0), Num(4.0)), Num(3.0)))




  }

  "parser" should "respect precedence" in {
    assert(ExprParser.parse("1 - 2 * 3") === BinOp("-", Num(1.0), BinOp("*", Num(2.0), Num(3.0))))
    // add additional tests
    assert(ExprParser.parse("1 - 2 / 3") === BinOp("-", Num(1.0), BinOp("/", Num(2.0), Num(3.0))))
    assert(ExprParser.parse("1 * 2 - 3") === BinOp("-",BinOp("*",Num(1.0),Num(2.0)),Num(3.0)))
    assert(ExprParser.parse("3 - 5 * 4") === BinOp("-", Num(3.0), BinOp("*", Num(5.0), Num(4.0))))
  }

  "parser" should "correctly parse let expressions" in {
    assert(ExprParser.parse("let x = 1 in x") ===
           Let("x", Num(1.0), Var("x")))
    assert(ExprParser.parse("let foo = 42 in bar") ===
           Let("foo", Num(42.0), Var("bar")))
    assert(ExprParser.parse("let x = 1 in 2 * 3 * x") ===
           Let("x", Num(1.0), BinOp("*", BinOp("*", Num(2.0), Num(3.0)), Var("x"))))
    // add additional tests
    assert(ExprParser.parse("let x = 2 in 2 * 3 * x") ===
           Let("x", Num(2.0), BinOp("*", BinOp("*", Num(2.0), Num(3.0)), Var("x"))))
    assert(ExprParser.parse("let x = 1 in 2 / 3 * x") ===
           Let("x", Num(1.0), BinOp("*", BinOp("/", Num(2.0), Num(3.0)), Var("x"))))
    assert(ExprParser.parse("let x = 1 in 2 * 3 - x") ===
           Let("x", Num(1.0), BinOp("-", BinOp("*", Num(2.0), Num(3.0)), Var("x"))))
  }

  "eval" should "correctly evaluate simple arithmetic in the AST" in {
    assert(eval(Map(), BinOp("*", Num(1.0), Num(2.0))) === 2.0)
    assert(eval(Map(), BinOp("*", BinOp("*", Num(1.0), Num(2.0)), Num(3.0))) === 6.0)
    assert(eval(Map(), BinOp("-", Num(1.0), BinOp("*", Num(2.0), Num(3.0)))) === -5.0)
    // add additional tests
    assert(eval(Map(), BinOp("*", BinOp("*", Num(2.0), Num(2.0)), Num(3.0))) === 12.0)
    assert(eval(Map(), BinOp("*", BinOp("/", Num(2.0), Num(2.0)), Num(3.0))) === 3.0)
    assert(eval(Map(), BinOp("+", BinOp("*", Num(1.0), Num(2.0)), Num(3.0))) === 5.0)
  }

  "eval" should "correctly evaluate let expressions in the AST" in {
    assert(eval(Map(), Let("x", Num(1.0), Var("x"))) === 1.0)
    intercept[NoSuchElementException] {	    
      eval(Map(), Let("foo", Num(42.0), Var("bar")))
    }
    assert(eval(Map("bar" -> 42.0), Let("foo", Num(42.0), Var("bar"))) == 42.0)
    assert(eval(Map("foo" -> 42.0), Let("foo", Num(47.0), Var("foo"))) == 47.0)
    assert(eval(Map(), Let("x", Num(1.0), BinOp("*", BinOp("*", Num(2.0), Num(3.0)), Var("x")))) === 6.0)
    // add additional tests
    assert(eval(Map(), Let("x", Num(1.0), BinOp("*", BinOp("*", Num(3.0), Num(3.0)), Var("x")))) === 9.0)
    assert(eval(Map(), Let("x", Num(1.0), BinOp("/", BinOp("*", Num(2.0), Num(3.0)), Var("x")))) === 6.0)
    assert(eval(Map(), Let("x", Num(2.0), BinOp("*", BinOp("*", Num(2.0), Num(3.0)), Var("x")))) === 12.0)
  }

  "evaluate" should "correctly parse and evaluate let expressions" in {
    assert(evaluate("let x = 1 in x") === 1.0)
    assert(evaluate("let x = 1 + 2 * 3 in 2 * x") === 14.0)
    assert(evaluate("(let x = 1 + 2 * 3 in 2 * x) * (let y = 2 in y * 2)") === 56.0)
    assert(evaluate("let x = 1 + 2 * 3 in x * (let x = 2 in x * 3 + 5) * x") === 7.0 * 11.0 * 7.0)
    // add additional tests
    assert(evaluate("let x = 2 + 2 * 3 in 2 * x") === 16.0)
    assert(evaluate("(let x = 1 + 2 * 3 in 4 * x) * (let y = 2 in y * 2)") === 28.0*4.0)
    assert(evaluate("let x = 2 + 2 * 3 in x * (let x = 2 in x * 3 + 5) * x") === 8.0 * 11.0 * 8.0)
  }

}