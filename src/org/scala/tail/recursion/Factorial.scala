

package org.scala.tail.recursion

object Factorial {
  // we could start by the old way of doing this ..
  def factorialImperativeStyle(n: Int) = {

    var fact = 1;
    for (i <- 1 to n) {
      fact = fact * i
    }
    fact
  }

  /* This code will works fine, but the once we insert larger numbers
   * it will result in stack overflow error
   */
  def factorialRecursive(n: Int): Int = {

    if (n == 1)
      1
    else
      n * factorialRecursive(n - 1)

  }
  /*One disadvantage of this code is that we have lost the simplicity of the caller,
  since now we have to pass two parameters instead of one.  */
  @scala.annotation.tailrec
  def factorialTailCallOptimised(n: Int, fact: BigInt): BigInt = {

    if (n == 1)
      fact
    else
      /* this will result in a compilation error once we annotate the 
      function with tailrec annotation */
      //1 * factorialTailCallOptimised(n - 1, fact * n)
      factorialTailCallOptimised(n - 1, fact * n)
  }

  /* lets start refactoring the above function to make it simpler to user
  we could do the following. 
  
  Step 1
  
  */

  def factorialStep1(n: Int) = factorialTailCallOptimised(n, BigInt(1))

  /*
   Step 2
   
   Having said that some one can still call the factorialTailCallOptimised function,
   so the next step could be encapsulating the factorialTailCallOptimised so here is 
   the functional way of encapsulating the the function rather than using a class.
   
   final code 

   * */

  def factorial(n: Int) = {
    
    // Functional style function  within function
    @scala.annotation.tailrec
    def factorialTailCallOptimised(n: Int, fact: BigInt): BigInt = {

      if (n == 1)
        fact
      else
        /* this will result in a compilation error once we annotate the 
      function with tailrec annotation */
        //1 * factorialTailCallOptimised(n - 1, fact * n)
        factorialTailCallOptimised(n - 1, fact * n)
    }
    
    factorialTailCallOptimised(n, BigInt(1))
  }

  def main(args: Array[String]): Unit = {

    /* 
    this will result in a StackOverflow error 
		println(factorialRecursive(5000000))

    Tail call optimize but we lost the simplicity
    println(factorialTailCallOptimised(5, BigInt(1)))
    
    Refactoring step 1
    println(factorialStep1(5))
     
     */
    
    println(factorial(500))

  }

}