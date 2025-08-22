fun main() {
    println(tamanhoSeguro("Kotlin"))
    println(tamanhoSeguro(""))
    println(tamanhoSeguro(null))
}

fun tamanhoSeguro(texto: String?): Int {
    if(texto == null)
    	return 0
    
    var tamanhoTexto: Int = 0
    
    for(char in texto) {
        tamanhoTexto++
    }
    
    return tamanhoTexto

    // todo esse código de cima pode ser substituído por essa única linha:
    // return texto?.length ?: 0
}