import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.immutable.VectorBuilder

class ComprehensionSpec extends FlatSpec {

	val theList = List(1,2,3,5,6,7,8,10,13,14,17)
		behavior of "This test should return a specific list"
			"Lists" should "be equal" in {
				assert(Comp.oddLT10(theList) === List(1,3,5,7))
			}

	val activities = Vector(Activity("01-01", "Run"), Activity("01-03", "Ski"), Activity("01-04", "Run"),
		Activity("01-10","Ski"), Activity("01-03", "Run"))
		behavior of "This test should return a Vector of Strings specifying date activity occured on"
			"Skiing" should "be on the right dates" in {
				assert(Comp.getDates("Ski", activities) === Vector("01-03", "01-10"))
			}
			"Running" should "be on the right dates" in {
				assert(Comp.getDates("Run", activities) === Vector("01-01", "01-04", "01-03"))
			}
			"Biking" should "return an empty Vector()" in {
				assert(Comp.getDates("Bike", activities) === Vector())
			}

		behavior of "This test should return a vector of the activities from a specified dates"
			"Date 01-01" should "return run" in {
				assert(Comp.getActivities("01-01", activities) === Vector("Run"))
			}
			"Date 01-02" should "return an empty Vector()" in {
				assert(Comp.getActivities("01-02", activities) === Vector())
			}
			"Date 01-03" should "return ski and run" in {
				assert(Comp.getActivities("01-03", activities) === Vector("Ski", "Run"))
			}
			"Date 01-04" should "return run" in {
				assert (Comp.getActivities("01-04", activities) === Vector("Run"))
			}
			"Date 01-10" should "return ski" in {
				assert (Comp.getActivities("01-10", activities) === Vector("Ski"))
			}

}

class FunctionsAsObjects extends FlatSpec {
	behavior of "This test checks if anonymous function is working"
		"anonymous function" should "return the correct dog years" in {
			assert(Funct.dogYears(10) === 70)
		}
	behavior of "This test checks anonymous function with multiple args"
		"anonymous function" should "return correct boolean" in {
			assert(Funct.between(70, 80, 90) === false)
			assert(Funct.between(70, 60, 90) === true)
		}

	var s = ""
	val numbers = Vector(1, 2, 5, 3, 7)
	numbers.foreach(x=>(s = s + (x*x + " ")))

	behavior of "This test uses an anonymous function with foreach"
		"anonymous function" should "return changed vector" in {
			assert(s === "1 4 25 9 49 ")
		}
	behavior of "This test makes simple plural of a string"
		"anonymous function" should "add s to string" in {
			assert(Funct.pluralize("cat") === "cats")
			assert(Funct.pluralize("dog") === "dogs")
			assert(Funct.pluralize("silly") === "sillys")
		}
}

class MapReduce extends FlatSpec {
	val v = Vector(1, 2, 3, 4)
	behavior of "This test uses map to do arithmetic"
		"anonymous function with map" should "mulitpy by 11 and add 10"
			assert(v.map(n => n*11 + 10) === Vector(21, 32, 43, 54))

	behavior of "This test uses for to do arithmetic instead of map"
		"anonymous funcition with for" should "multiply by 11 and add 10 while returning a vector" in {
			assert(Rmap.vbuild(v) === Vector(21, 32, 43, 54))
		}
		"anonymous function with for" should "add 1 to each value" in {
			assert(Rmap.v1build(v) === Vector(2, 3, 4, 5))
		}
	behavior of "this test uses reduce to do arithmetic on sequence"
		"sumIt" should "sum arguments from list" in {
			assert(Rmap.sumIt(1,2,3) === 6)
			assert(Rmap.sumIt(45,45,45,60) === 195)
		}
}

class Brevity extends FlatSpec {
	behavior of "This test checkes refactored code"
		"assignResult" should "still work if refactored" in {
			assert(Revity.assignResult(true) === 42)
			assert(Revity.assignResult(false) === 47)
		}
		"assignResult1" should "still work after removing curly braces" in {
			assert(Revity.assignResult1(true) === 42)
			assert(Revity.assignResult1(false) === 47)
		}
		"assignResult2" should " still work after removing the return type" in {
			assert(Revity.assignResult2(true) === 42)
			assert(Revity.assignResult2(false) === 47)
		}
	val doubleHalfCaf = new Coffee(shots=2, decaf=1)
	val tripleHalfCaf = new Coffee(shots=3, decaf=2)
	behavior of "This test checks coffee refactoring"
		"doubleHalfCaf" should "result in correct number" in {
			assert(doubleHalfCaf.decaf === 1)
			assert(doubleHalfCaf.caf() === 1)
			assert(doubleHalfCaf.shots === 2)
		}
		"tripleHalfCaf" should "result in correct number" in {
			assert(tripleHalfCaf.decaf === 2)
			assert(tripleHalfCaf.caf() === 1)
			assert(tripleHalfCaf.shots === 3)
		}

}

class Zip extends FlatSpec {
	val people = Vector("Sally Smith", "Dan Jones", "Tom Brown", "Betsy Blanc", "Stormy Morgan", "Hal Goodsen")
	val size = people.size
	val group1 = people.take(size/2)
	val group2 = people.takeRight(size/2)
	val pairs = group1 zip group2
	behavior of "This test groups people with zip"
		"zip" should "group correctly" in {
			assert(pairs === Vector(("Sally Smith", "Betsy Blanc"), ("Dan Jones", "Stormy Morgan"), ("Tom Brown", "Hal Goodsen")))
		}
	val people1 = Vector("Sally Smith", "Dan Jones", "Tom Brown", "Betsy Blanc", "Stormy Morgan")
	val size1 = people1.size
	val group11 = people1.take(size1/2)
	val group21= people1.takeRight(size1/2)
	val pairs1 = group11 zip group21
	behavior of "This test groups of odd number of people with zip"
		"zip" should "order accordingly" in {
			assert(pairs1 === Vector(("Sally Smith", "Betsy Blanc"), ("Dan Jones", "Stormy Morgan")))
		}
	val people2 = List("Sally Smith", "Dan Jones", "Tom Brown", "Betsy Blanc", "Stormy Morgan", "Hal Goodsen")
	val size2 = people2.size
	val group12 = people2.take(size2/2)
	val group22= people2.takeRight(size2/2)
	val pairs2 = group12 zip group22
	behavior of "This test groups of odd number of people in list with zip"
		"This zip" should "order accordingly" in {
			assert(pairs2 === List(("Sally Smith", "Betsy Blanc"), ("Dan Jones", "Stormy Morgan"), ("Tom Brown", "Hal Goodsen")))
		}
}