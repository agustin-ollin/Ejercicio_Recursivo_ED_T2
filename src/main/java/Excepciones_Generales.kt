import java.lang.Exception
import kotlin.Throws

/**
 * Clase de Excepciones generales, genera las excepciones necesarias para que el programa envie mensajes de error
 * en caso de que se ingresen datos no válidos
 */
class Excepciones_Generales(message: String?) : Exception(message) {
    companion object {
        /**
         * Método que genera una excepción en caso de que en el rango de valores generados el número inferior sea mayor al superior
         */
        @Throws(Excepciones_Generales::class)
        fun comprobar_Rango(min: Int, max: Int) {
            if (min > max) {
                throw Excepciones_Generales("Ingrese un rango válido")
            }
        }

        /**
         * Método genera una excepción en caso de que la longitud de la lista sea menor o igual a cero
         */
        @Throws(Excepciones_Generales::class)
        fun restringir_Negativos(numero: Int) {
            if (numero <= 0) {
                throw Excepciones_Generales("Ingrese una lóngitud positiva para la lista")
            }
        }
    }
}