fun main() {
    val animais = listOf(Gato(), Gato(), Gato())
    Fazenda.emitirSons(animais)
}

abstract class Animal() {
    abstract fun som()
}

class Gato() : Animal() {
    override fun som() {
        println("Miau")
    }
}

object Fazenda {
    fun emitirSons(animais: List<Animal>) {
        for(animal in animais) {
            animal.som()
        }
    }
}