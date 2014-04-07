import Math.max

object Person {
		def calculateBMI(lbs: Double, height: Double) = {
			val bmi = lbs/ (height*height) * 703.07
			if (bmi < 18.5) "Underweight"
			else if (bmi < 25) "Normal Weight"
			else "Overweight"
	}
}


object Word {
		def isPalindrome(test: String): Boolean = {
			if (test == test.reverse) true
			else false
		}
		def isPalIgnoreCase(str: String): Boolean = {
			val s = str.toUpperCase.reverse
			s.equals(str.toUpperCase)
		}
	}

object Pattern{
def oneOrTheOther(exp:Boolean):String = exp match {
			case true => "True!"
			case _ => "It's false"
		}
}

	case class Passenger(first:String, last:String)
	case class Train(travelers:Vector[Passenger], line:String)
	case class Bus(passengers:Vector[Passenger], capacity:Int)
	case class Plane(passengers:Vector[Passenger], plane:String)
	case class Kitten(name:String)

object travels{
	def travel(transport:Any):String = {
		transport match {
			case Train(travelers, line) => s"Train line $line $travelers"
			case Bus(travelers, seats) => s"Bus size $seats $travelers"
			case Plane(travelers, equipment) => s"Plane $equipment $travelers"
			case Passenger(first,last) => first + " is walking"
			case what => s"$what is in limbo!"
		}
	}
}

object Listmax {
	def findMax(aList:List[Int], theMax: Int): Int = {
		if (aList.isEmpty) theMax
		else findMax(aList.tail, max(theMax, aList.head))
	}
}

