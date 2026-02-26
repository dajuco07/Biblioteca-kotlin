package models

/**
 * Classe base per a persones de la biblioteca.
 * Mostra l'ús d'herència al projecte (RA4.7).
 */
open class Persona(
    val id: String,
    val nom: String
) {
    open fun infoPersonal(): String = "Nom: $nom"
}
