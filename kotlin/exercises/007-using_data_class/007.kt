fun main() {
    val p = Produto("Notebook", 3000.0)
    val pComDesconto = p.desconto(10.0)

    println(pComDesconto)
}

data class Produto(val nome: String, val preco: Double) {
    fun desconto(percentual: Double): Produto {
        return Produto(nome, preco * (1 - percentual / 100))
    }
}