val nums = List(1,2,3,4,5,6,7,8,9)
val oddNums = nums.filter((x: Int) => x % 2 != 0)
for (n <- oddNums) {println(n)}

def cube(x: Int) : Int = {x * x * x}
def applyFunc(x: Int, f: Int => Int): Int = {
  f(x)
}

for (n <- oddNums.map(cube)) {println(n)}