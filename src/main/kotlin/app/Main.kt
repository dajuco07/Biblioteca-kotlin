package app
import persistence.BibliotecaStorage
import core.Biblioteca
import models.Llibre
import models.Lector
import utils.Utils
fun mostrarMenu() {
    println(
        """
        -------------------------------
        GESTIÓ DE BIBLIOTECA
        -------------------------------
        1. Registrar llibre
        2. Registrar lector
        3. Prestar llibre
        4. Retornar llibre
        5. Llistar llibres disponibles
        6. Cercar llibres per autor
        0. Sortir
        -------------------------------
        """.trimIndent()
    )
}

fun registrarLlibre(biblioteca: Biblioteca) {
    print("ID del llibre: ")
    val id = readln()

    print("Títol: ")
    val titol = readln()

    print("Autor: ")
    val autor = readln()

    val llibre = Llibre(id, titol, autor)

    if (biblioteca.afegirLlibre(llibre)) {
        println("Llibre registrat correctament.")
    } else {
        println("Ja existeix un llibre amb aquest ID.")
    }
}


fun registrarLector(biblioteca: Biblioteca) {
    print("ID del lector: ")
    val id = readln()

    print("Nom del lector: ")
    val nom = readln()

    val lector = Lector(id, nom)

    if (biblioteca.registrarLector(lector)) {
        println("Lector registrat correctament.")
    } else {
        println("Ja existeix un lector amb aquest ID.")
    }
}

fun prestarLlibre(biblioteca: Biblioteca) {
    println("Llibres disponibles:")
    biblioteca.llistarDisponibles()
    println()

    print("ID del llibre: ")
    val idLlibre = readln()

    val llibre = biblioteca.cataleg.find { it.id == idLlibre }
    if (llibre == null) {
        println("Llibre no trobat.")
        return
    }

    print("ID del lector: ")
    val idLector = readln()

    val lector = biblioteca.lectors.find { it.id == idLector }
    if (lector == null) {
        println("Lector no trobat.")
        return
    }

    lector.prestarLlibre(llibre)
}


fun retornarLlibre(biblioteca: Biblioteca) {
    print("ID del llibre: ")
    val idLlibre = readln()

    val llibre = biblioteca.cataleg.find { it.id == idLlibre }
    if (llibre == null) {
        println("Llibre no trobat.")
        return
    }

    print("ID del lector: ")
    val idLector = readln()

    val lector = biblioteca.lectors.find { it.id == idLector }
    if (lector == null) {
        println("Lector no trobat.")
        return
    }

    lector.retornarLlibre(llibre)
}

fun cercarPerAutor(biblioteca: Biblioteca) {
    print("Autor: ")
    val autor = readln()

    val resultats = biblioteca.cercarPerAutor(autor)

    if (resultats.isEmpty()) {
        println("No s'han trobat llibres d'aquest autor.")
    } else {
        resultats.forEach {
            println(it.info())
        }
        println("Total: ${Utils.comptarPerAutor(resultats, autor)}")
    }
}

fun main() {

    val biblioteca = BibliotecaStorage.carregarJSON()
    var opcio: Int

    do {
        mostrarMenu()
        opcio = readln().toIntOrNull() ?: -1

        when (opcio) {
            1 -> registrarLlibre(biblioteca)
            2 -> registrarLector(biblioteca)
            3 -> prestarLlibre(biblioteca)
            4 -> retornarLlibre(biblioteca)
            5 -> biblioteca.llistarDisponibles()
            6 -> cercarPerAutor(biblioteca)
            0 -> {
                BibliotecaStorage.desarJSON(biblioteca)
                println("Dades desades. Sortint del programa.")
            }
            else -> println("Opció no vàlida.")
        }

    } while (opcio != 0)
}
