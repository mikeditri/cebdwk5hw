//write a function that prints the following two greetings
//Hello <name>, How are you doing?
//Hello <name>, How was your day?


def greet1(name: String) : String = {
s"Hello $name how are you doing?"
}

def greet2(name: String) : String = {
  s"Hello $name how was your day?"
}


def frame(x: String, f: String => String): String = {
  f(x)
}

println(frame("Alice",greet1))
println(frame("Nick",greet2))
