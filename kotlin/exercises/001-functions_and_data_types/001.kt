fun main() {
    val nome: String = "Alex"
    val idade: Int = 24
    val altura: Double = 1.69

    println(informacoes(nome, idade, altura))
}

fun informacoes(nome: String, idade: Int, altura: Double): String {
    return "Nome: $nome, Idade: $idade, Altura: $altura m"
}