package models

/**
 * Representa un lector que pot demanar llibres en préstec.
 * Hereta de Persona (RA4.7).
 */
class Lector(
    id: String,
    nom: String
) : Persona(id, nom) {

    /**
     * Llibres actualment prestats per aquest lector.
     * La llista és privada (encapsulació).
     */
    private val llibresPrestats: MutableList<Llibre> = mutableListOf()

    /**
     * Retorna una còpia de només-lectura dels llibres prestats.
     */
    fun getLlibresPrestats(): List<Llibre> = llibresPrestats.toList()

    /**
     * Presta un llibre a aquest lector, si està disponible.
     */
    fun prestarLlibre(llib: Llibre) {
        if (!llib.disponible) {
            // No imprimim res aquí per evitar missatges duplicats al carregar.
            // La lògica de negoci principal la gestiona el menú de l'app.
            return
        }

        llib.prestar()
        if (!llibresPrestats.contains(llib)) {
            llibresPrestats.add(llib)
        }
        // No imprimim missatges per no saturar la consola durant la càrrega inicial.
    }

    /**
     * Retorna un llibre que aquest lector tenia en préstec.
     */
    fun retornarLlibre(llib: Llibre) {
        if (llibresPrestats.contains(llib)) {
            llib.retornar()
            llibresPrestats.remove(llib)
            println("$nom ha retornat el llibre \"${llib.titol}\".")
        } else {
            println("$nom no té aquest llibre prestat.")
        }
    }

    /**
     * Mostra tots els llibres que aquest lector té actualment prestats.
     */
    fun llistarPrestecs() {
        if (llibresPrestats.isEmpty()) {
            println("$nom no té cap llibre prestat.")
        } else {
            println("Llibres prestats per $nom:")
            llibresPrestats.forEach { llibre ->
                println(" - ${llibre.info()}")
            }
        }
    }

    /**
     * Retorna el nombre de llibres actualment prestats per aquest lector.
     */
    fun comptarPrestecs(): Int = llibresPrestats.size
}
