import org.scalatest._
import Lab1._

class Problem3Spec extends FlatSpec {

  // Check that abs works for negative, positive and zero values

  "abs" should "evaluate to the absolute value of the argument" in {
     assert(Lab1.abs(-7.12) === 7.12)
     assert(Lab1.abs(7.12) === 7.12)
     assert(Lab1.abs(0) === 0)
  }

  // Check that xor works for all four combinations of boolean values

  "xor" should "evaluate to the exclusive or of the arguments" in {
     assert(Lab1.xor(false, true) === true)
     assert(Lab1.xor(true, false) === true)
     assert(Lab1.xor(false, false) === false)
     assert(Lab1.xor(true, true) === false)
  } 
}
