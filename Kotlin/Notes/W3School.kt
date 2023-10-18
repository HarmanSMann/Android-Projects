// 1 - Syntax Lessons
    // 1
        fun main () {
          println("Hello World")
        }
    //2
        // 1 line comment
    //3
        /* multi-line comment*/

// 2 - Variables
    //1
        var carName = "Volvo"
    //2
        val birthyear = 1975
    //3
print
        var carName = "Volvo"
        println(carName)
    //4
        val x = 5 //non mutable -> val
        val y = 10
        println(x + y)

//3 - Data Types
    // -> Int, String, Boolean

//4 - Operators
    //1
        println(10 * 5)
    //2
        println(10 / 5)
    //3
        val x = 5
        ++x
    //4
        val x = 5
        val y = 3
        println(x == y)

//5 - Strings
        var carName = "Volvo"
        println(txt1.compareTo(txt2))

// 6 - Boolean
        val x = 10
        val y = 9
        println(x > y)

//7 - if...Else

val time = 20
val greeting = if (time <18 ) {
    "Good day"
    } else {
    "Good Evening"
}
println(greeting)       


//8 - While
var i = 0
while (i < 10) {
  if (i == 4) {
    i++
    continue //skip
    //break leave loop
  }
  println(i)
  i++
}

//9 - Arrays
val cars = arrayOf("Volvo", "BMW", "Ford", "Mazda")
//same as java, [#], .size

// 10 - For
for (x in cars) //do something
for (nums in 5..15) //so something

//11 - Functions

fun myFunction(fname: String) {
  println(fname + " Doe")
}

fun main() {
  myFunction("John")
}

//11 - Classes
class Car(var brand: String, var model: String, var year: Int)

fun main() {
  val c1 = Car("Ford", "Mustang", 1969)
  println(c1.brand)
}

open class MyClass
