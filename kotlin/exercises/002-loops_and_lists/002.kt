fun main() {
    print(paresAteN(20))
}

fun paresAteN(n: Int): List<Int> {
    val pares = mutableListOf<Int>();

    for(i in 0..n) {
        if(i % 2 == 0)
            pares.add(i);
    }

    return pares
}