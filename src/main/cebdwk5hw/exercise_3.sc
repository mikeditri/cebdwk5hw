//Write a max function that picks the max of two numbers and another get_max function
//to call the first one with inputs.

def max(x: Int, y:Int) : Int = {
  if ( x > y)
  {x}
  else
  {y}
}
def get_max(x: Int, y: Int, f: (Int,Int) => Int): Int = {
  f(x,y)
}

println(get_max(100,200,max))