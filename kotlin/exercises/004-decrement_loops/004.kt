fun main() {
    println(fatorial(5))
}

fun fatorial(n: Int): Long {
    var resultado: Long = 1L
    
  	for(i in n downTo 1) {
        resultado *= i
    }
    
    return resultado
}