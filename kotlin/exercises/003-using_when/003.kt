fun main() {
    println(classificarNota(7.5))
    println(classificarNota(5.0))
    println(classificarNota(3.0))
}

fun classificarNota(nota: Double): String {
    when {
        nota > 5.9 -> return "Aprovado"
        nota > 4 -> return "Recuperação"
        else -> return "Reprovado"
    }
}