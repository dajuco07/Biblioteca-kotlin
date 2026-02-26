package core

import models.Lector
import models.Llibre

/**
 * Classe que gestiona el catàleg de llibres i els lectors registrats.
 */
class Biblioteca {

    /**
     * Catàleg de llibres de la biblioteca.
     */
    val cataleg: MutableList<Llibre> = mutableListOf()

    /**
     * Lectors registrats a la biblioteca.
     */
    val lectors: MutableList<Lector> = mutableListOf()

    /**
     * Afegeix un llibre al catàleg.
     */
    fun afegirLlibre(llibre: Llibre): Boolean {
        if(cataleg.any {it.id == llibre.id}){
            return false
        }
        cataleg.add(llibre)
        return true
    }
    /**
     * Registra un lector a la biblioteca.
     */
    fun registrarLector(lector: Lector): Boolean {
        if(lectors.any {it.id == lector.id}){
            return false
        }
        lectors.add(lector)
        return true
    }

    /**
     * Mostra només els llibres que NO estan prestats.
     */
    fun llistarDisponibles() {
        val disponibles = cataleg.filter { it.disponible }
        if (disponibles.isEmpty()) {
            println("No hi ha llibres disponibles ara mateix.")
        } else {
            println("Llibres disponibles al catàleg:")
            disponibles.forEach { llibre ->
                println(" - ${llibre.info()}")
            }
        }
    }

    /**
     * Retorna tots els llibres del catàleg escrits per un autor concret.
     */
    fun cercarPerAutor(autor: String): List<Llibre> {
        return cataleg.filter { it.autor.equals(autor, ignoreCase = true) }
    }
    fun copia(): Biblioteca {
        val novaBiblioteca = Biblioteca()
        novaBiblioteca.cataleg.addAll(this.cataleg)
        novaBiblioteca.lectors.addAll(this.lectors)
        return novaBiblioteca
    }
}
