import org.scalatest.FlatSpec
import scala.collection.mutable.Stack

import Math.max
//
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


class calculateBMI extends FlatSpec {
	/*object Person {
=======
	object Person {
>>>>>>> bd55534b85c4f7172d4d9e90b7dab253030886c3
		def calculateBMI(lbs: Double, height: Double) = {
			val bmi = lbs/ (height*height) * 703.07
			if (bmi < 18.5) "Underweight"
			else if (bmi < 25) "Normal Weight"
			else "Overweight"
<<<<<<< HEAD
	}*/

	

	behavior of "This test should tell you whether you" 
			it should "be normal weight" in {
			assert((Person.calculateBMI(160, 68))===("Normal Weight"))}
			
			it should "be underweight" in {
			assert((Person.calculateBMI(100, 68))===("Underweight"))}
			
			it should "be overweight" in {
			assert((Person.calculateBMI(200, 68))===("Overweight"))
				}
			}


class Palindrome extends FlatSpec {
	/*object Word {
=======
}

class Palindrome extends FlatSpec {
	object Word {
>>>>>>> bd55534b85c4f7172d4d9e90b7dab253030886c3
		def isPalindrome(test: String): Boolean = {
			if (test == test.reverse) true
			else false
		}
		def isPalIgnoreCase(str: String): Boolean = {
			val s = str.toUpperCase.reverse
			s.equals(str.toUpperCase)
		}
	}
<<<<<<< HEAD
	*/
	
	behavior of "This test tells you whether a word is a palindrome"
		"mom" should "be a palindrome" in {
			assert(Word.isPalindrome("mom") === true)
		}
		"dad" should "be a palindrome" in {
			assert(Word.isPalindrome("dad") === true)
		}
		"street" should "not be a palindrome" in {
			assert(Word.isPalindrome("street") === false)
		}
		
	behavior of "This test tells whether a case is an ignore case or not"
		"Bob" should "be an ignore case" in {
			assert(Word.isPalIgnoreCase("Bob") === true)
		}
		"DAD" should "be an ignore case" in {
			assert(Word.isPalIgnoreCase("DAD") === true)
		}
		"Blob" should "not be an igore case" in {
			assert(Word.isPalIgnoreCase("Blob") === false)
		}
			
}


class PatternMatching extends FlatSpec {
		/*def oneOrTheOther(exp:Boolean):String = exp match {
			case true => "True!"
			case _ => "It's false"
		}
	*/
		/*def oneOrTheOther(exp:Boolean):String = exp match {
			case true => "True!"
			case _ => "It's false"
		}
	*/
	val v = Vector(1)
	val v2 = Vector(3,4)
	
	behavior of "This test should tell you if a vector is equal to its reversed self"
		"A vector of a single item" should "be true" in {
			assert(Pattern.oneOrTheOther( v == v.reverse) === "True!")
		}
		"A vector of more than one thing which are not the same" should "be false" in {
			assert(Pattern.oneOrTheOther( v2 == v2.reverse) === "It's false")

		}
}
	
class PatternMatchingWithCaseClasses extends FlatSpec {
	/*
=======
>>>>>>> bd55534b85c4f7172d4d9e90b7dab253030886c3
	case class Passenger(first:String, last:String)
	case class Train(travelers:Vector[Passenger], line:String)
	case class Bus(passengers:Vector[Passenger], capacity:Int)
	case class Plane(passengers:Vector[Passenger], plane:String)
	case class Kitten(name:String)
	
	def travel(transport:Any):String = {
		transport match {
			case Train(travelers, line) => s"Train line $line $travelers"
			case Bus(travelers, seats) => s"Bus size $seats $travelers"
			case Plane(travelers, equipment) => s"Plane $equipment $travelers"
			case Passenger(first,last) => first + " is walking"
			case what => s"$what is in limbo!"
		}
	}
<<<<<<< HEAD
	*/
	
	val travelers = Vector(Passenger("Harvey", "Rabbit"),Passenger("Dorothy", "Gale"))
	val trip = Vector(Train(travelers, "Reading"), Bus(travelers, 100))
	val trip2 = Vector(Train(travelers, "Reading"),Plane(travelers, "B757"),Bus(travelers, 100))
	
	behavior of "This test is to check the output of the trip"
		"Trip 0" should " be correct" in {
			assert(travels.travel(trip(0)) === "Train line Reading " + "Vector(Passenger(Harvey,Rabbit), " + "Passenger(Dorothy,Gale))")
		}
		"Trip 1" should " be correct" in {
			assert(travels.travel(trip(1)) === "Bus size 100 " + "Vector(Passenger(Harvey,Rabbit), " + "Passenger(Dorothy,Gale))")
		}
		"Trip 2" should " be correct" in {
			assert(travels.travel(trip2(1)) === "Plane B757 " + "Vector(Passenger(Harvey,Rabbit), " + "Passenger(Dorothy,Gale))")
		}
		"Exercise 2" should " be correct" in {
			assert(travels.travel(Passenger("Sally", "Marie")) === "Sally is walking")
		}
		"Exercise 3" should " be correct" in {
			assert(travels.travel(Kitten("Kitty")) === "Kitten(Kitty) is in limbo!")
		}
}

class List_max extends FlatSpec {
	/*def findMax(aList:List[Int], theMax: Int): Int = {
		if (aList.isEmpty) theMax
		else findMax(aList.tail, max(theMax, aList.head))
	}
	*/
	val aList = List(10,20,45,15,30)
	behavior of "This test returns the max number of a list"
		"Exercise 1" should "be correct" in {
			assert(Listmax.findMax(aList, 0) === 45)
	
}
}
	



