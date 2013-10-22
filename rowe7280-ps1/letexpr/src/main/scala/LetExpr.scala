import scala.util.parsing.combinator._


object LetExpr {

  sealed abstract class Expr
  case class Var(name: String) extends Expr
  case class Num(num: Double) extends Expr
  case class BinOp(op: String, left: Expr, right: Expr) extends Expr
  case class Let(name: String, value: Expr, body: Expr) extends Expr

  object ExprParser extends JavaTokenParsers {

    // Grammar based in part on this example in the Scala source code, specifically arithmeticParsers2
    // https://github.com/scala/scala-dist/blob/master/examples/src/main/scala/parsing/ArithmeticParsers.scala
    
    // Use `foldLeft` to convert concrete parse tree of binary ops to AST,
    // while maintaining left-right associativity
    //
    // The case expression here is already an anonymous (partial) function,
    // so we can directly assign to the name `reduceList`
    val reduceList: Expr ~ List[String ~ Expr] => Expr = {
      case lhs ~ rhs => rhs.foldLeft(lhs)(reduce) 
    }

    // Create a binop from an infix operator
    def reduce(left: Expr, right: String ~ Expr) = BinOp(right._1, left, right._2)

    def expr  : Parser[Expr] = letexpr | term ~ rep ("+" ~ term | "-" ~ term) ^^ reduceList
    def term  : Parser[Expr] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ reduceList
    def factor: Parser[Expr] = "(" ~> expr <~ ")" | floatingPointNumber ^^ {x => Num(x.toDouble)} | variable
    def variable: Parser[Var] = ident ^^ { s => Var(s)} 
    def let: Parser[String] = "let\\b".r
    def letexpr: Parser[Expr] = "let" ~ ident ~ "=" ~ expr ~ "in" ~ expr ^^ {case _ ~ i ~ _ ~ x ~ _ ~ e => Let(i, x, e)}

    // NOTE: can select the desired parser combinator instead of
    // `expr` - might be useful for debugging
    def parseExpr(text: String) = parseAll(expr, text)
    def parse(text: String): Expr = parseExpr(text).get

  }

  def eval(env: Map[String, Double], e: Expr):Double = (e: @unchecked) match {
    case Num(n) => n
    case Var(x) => env(x)
    case BinOp("*", b, c) => eval(env, b)*eval(env,c)
    case BinOp("-", b, c) => eval(env, b)-eval(env,c)
    case BinOp("/", b, c) => eval(env, b)/eval(env,c)
    case BinOp("+", b, c) => eval(env, b)+eval(env,c)
    case Let(x, value, body) => 
      val n = env + (x ->eval(env, value))
      eval(n, body)
      
      
  }

  // Helper function for top-level evaluation from source text and empty environment
  def evaluate(text: String): Double = eval(Map(), ExprParser.parse(text))
 
  def main(args: Array[String]) {
    val input = args.mkString(" ")
    println("Parsing: " + input)
    val ast = ExprParser.parseExpr(input)
    println("Parsed:  " + ast)
    println("Eval:    " + eval(Map(), ast.get))
  }

}
