package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.Biblioteca
import models.Llibre
import models.Lector
import persistence.BibliotecaStorage

class LibraryState {

    var biblioteca by mutableStateOf(BibliotecaStorage.carregarJSON())
        private set

    fun afegirLlibre(id: String, titol: String, autor: String): Boolean {
        val nouLlibre = Llibre(id, titol, autor)
        if (biblioteca.afegirLlibre(nouLlibre)) {
            biblioteca = biblioteca.copia()
            return true
        }
        return false
    }

    fun registrarLector(id: String, nom: String): Boolean {
        val nouLector = Lector(id, nom)
        if (biblioteca.registrarLector(nouLector)) {
            biblioteca = biblioteca.copia()
            return true
        }
        return false
    }

    fun prestarLlibre(lectorId: String, llibreId: String): String {
        val lector = biblioteca.lectors.find { it.id == lectorId }
        if (lector == null) {
            return "Error: No se ha encontrado el lector con ID $lectorId."
        }

        val llibre = biblioteca.cataleg.find { it.id == llibreId }
        if (llibre == null) {
            return "Error: No se ha encontrado el libro con ID $llibreId."
        }

        if (!llibre.disponible) {
            return "Error: El libro '${llibre.titol}' no está disponible actualmente."
        }

        lector.prestarLlibre(llibre)
        biblioteca = biblioteca.copia()
        return "¡Éxito! El libro '${llibre.titol}' ha sido prestado a ${lector.nom}."
    }

    fun cercarPerAutor(autor: String): List<Llibre> {
        return biblioteca.cercarPerAutor(autor)
    }

    fun guardarDades() {
        BibliotecaStorage.desarJSON(biblioteca)
    }
}
