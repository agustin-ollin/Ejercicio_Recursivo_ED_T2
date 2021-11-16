/**
 * Clase Lista donde se generan las operaciones necesarias para realizar la búsqueda y resultados del ejercicio
 */
class Lista(val longitud: Int, val min: Int, val max: Int, val buscar: Int) {
    var lista: MutableList<Int>//Contiene todos los datos generados
    var lista_Busqueda: MutableList<Int>//Solo contiene aquellos valores obtenidos através de la búsqueda

    /**
     * Función para inicializar las funciones y realizar los cálculos al momento de instanciar un objeto de esta clase
     */
    init {
        lista = MutableList(longitud) {
            generar_NumeroAleatorio()
            // Otra forma de agregar el número aleatorio a la lista:
            // it -> (min..max).random()
        }
        lista_Busqueda = mutableListOf()
        generar_Busqueda(0)
    }

    /**
     * Método para generar un número aleatorio entre un rango de números
     */
    fun generar_NumeroAleatorio(): Int = (min..max).random()

    /**
     * Método Recursivo para guardar en una lista muteable los valores que coincidan con el número a buscar
     * Tiene como parámetros el índice en la lista que servirá como caso base ya que en el momento en que
     * el índice llegue a ser igual a la longitud entonces se terminará el método recursivo
     */
    fun generar_Busqueda(indice: Int) {
        if (indice < longitud) {
            if (lista.elementAt(indice) == buscar)
                lista_Busqueda.add(indice)
            generar_Busqueda(indice + 1)
        }
    }
}