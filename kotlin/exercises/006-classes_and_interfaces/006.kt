fun main() {
    val r: Imprimivel = Relatorio()
    println(r.imprimir())
}

interface Imprimivel {
    fun imprimir(): String
}

class Relatorio() : Imprimivel {
    override fun imprimir(): String {
        return "Gerando relatório..."
    }
}