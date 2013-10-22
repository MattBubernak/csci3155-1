case class Activity(date: String, action: String)


object Comp {

	def oddLT10(v:List[Int]):List[Int] = {
		val result = for {
			n <- v
			if n < 10
			isOdd = (n % 2 != 0)
			if(isOdd)
		} 
		yield n
        result
	}

	

	def getDates(action: String, activities: Vector[Activity]):Vector[String] = {
		for {
			a <- activities
				if a.action equals(action)
		}
		yield a.date
	}

	def getActivities(date: String, activities: Vector[Activity]):Vector[String] = {
		for {
			a <- activities
				if a.date equals (date)
		}
		yield a.action
	}

}

object Funct {
	val dogYears = (x:Int) => (x*7)
	val between = (t:Int, l:Int, h:Int) => if(t >= l && t <= h) true else false
	//problem 7 pg. 171 in AtomicSpec.scala
	val pluralize = (x:String) => (x + "s")

}

object Rmap {
	//problem 1 pg. 174 in AtomicSpec.scala test
	def vbuild(v: Vector[Int]):Vector[Int] = {
		var result = Vector[Int]()
		for {n <- v}
			result = result :+ ((n*11) + 10)
			result
	}
	def v1build(v: Vector[Int]):Vector[Int] = {
		var result = Vector[Int]()
		for {n <- v}
			result = result :+ (n+1)
			result
	}
	def sumIt(someNumbers: Int*):Int = {
		someNumbers.reduce((total, n) => total + n)
	}
}

class Coffee(val shots:Int = 2, val decaf:Int = 0, val milk:Boolean = false, val toGo:Boolean = false, val syrup:String = "") {
	var result = ""
	//println(caf(), decaf, milk, toGo, syrup)
	def getCup() = {
		if(toGo)
			result += "ToGoCup "
		else
			result += "HereCup "
	}
	def caf() = shots - decaf
	def pourShots() = {
		for(s <- 0 until shots)
			if(decaf > 0)
				result += "decaf shot "
			else
				result += "shot "
	}
	def addMilk() = {
		if(milk)
			result += "milk"
	}
	def addSyrup() = 
		result += syrup

	getCup()
	pourShots()
	addMilk()
	addSyrup()
}

object Revity {
	def assignResult(arg:Boolean):Int = {if(arg) 42 else 47}
	def assignResult1(arg:Boolean):Int = if(arg) 42 else 47
	def assignResult2(arg:Boolean) = if(arg) 42 else 47
}

//All problems from pg. 305 are in AtomicSpec.scala 