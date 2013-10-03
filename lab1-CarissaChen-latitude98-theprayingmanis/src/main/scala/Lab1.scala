object Lab1 {
  
  /*
   * CSCI 3155: Lab 1
   */

  /*
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
  
  /* Exercise 3a */

  def abs(n: Double): Double = {
    return if (n < 0) -n else n
  } 

  /* Exercise 3b */
  def xor(a: Boolean, b: Boolean): Boolean = {
    if (a < b) {true}
    else if (a > b) {true}
    else {false}
  }
  
  /* Exercise 4a */
  def repeat(s: String, n: Int): String = {
    n match {
      case 0 => ""
      case x if x < 0 => throw new IllegalArgumentException
      case _ => s + repeat(s, n-1)
    }
  }

  /* Exercise 4b */
  def sqrtStep(c: Double, xn: Double): Double = {
    return xn - (((xn*xn) - c)/(2*xn))
  }
  
  def sqrtN(c: Double, x0: Double, n: Int): Double = {
    if (n < 0) { throw new IllegalArgumentException }
    else if (x0 < 0) { throw new IllegalArgumentException }
    else if (n == 0) {return x0}
    else { sqrtN(c, sqrtStep(c,x0), n-1) }
  }
  
  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = {
    if (x0 < 0) { throw new IllegalArgumentException }
    else if (epsilon <= 0) { throw new IllegalArgumentException }
    else if (abs((x0*x0)-c) < epsilon) {return x0}
    else  { sqrtErr(c, sqrtStep(c, x0), epsilon) }
  }
  
  def sqrt(c: Double): Double = sqrtErr(c, 1.0, 0.0001)
  
  /* Exercise 5 */
  
  sealed abstract class SearchTree
  case object Empty extends SearchTree
  case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree
  
  def repOk(t: SearchTree): Boolean = {
    def check(t: SearchTree, min: Int, max: Int): Boolean = t match {
      case Empty => true
      case Node(l, d, r) =>
        if (d >= min && d <= max && check(l, min,d) && check(r, d, max)) {true}
        else {false}
    }
    check(t, Int.MinValue, Int.MaxValue)
  }
  
  def insert(t: SearchTree, n: Int): SearchTree = t match {
    case Empty => new Node(Empty, n, Empty)
    case Node(l, d, r) =>
      if (n >= d) { new Node(l, d,insert(r,n)) }
      else { new Node(insert(l,n), d, r) }
  }
  
  def deleteMin(t: SearchTree): (SearchTree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      case Node(Empty, d, r) => (r, d)
      case Node(l, d, r) =>
        val (l1,m) = deleteMin(l)
        (Node(l1,d,r),m)
        //throw new UnsupportedOperationException
    }
  }
 
  def delete(t: SearchTree, n: Int): SearchTree = t match {
    case Empty => t
    case Node(Empty, d, Empty) => if (n == d) {Empty} else {t}
    case Node(l, d, r) =>
    if (n < d){ Node(delete(l,n),d, r) }
    else if (n == d){
      val (l1,m) = deleteMin(r)
      Node(l, m, l1) //replace int to be deleted with inorder successor
    }
    else{ Node(l,d,delete(r,n)) }
  }

  
}
