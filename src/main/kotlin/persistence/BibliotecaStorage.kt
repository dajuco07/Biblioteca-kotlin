package persistence

import com.google.gson.GsonBuilder
import core.Biblioteca
import models.Lector
import models.Llibre
import persistence.dto.BibliotecaDTO
import persistence.dto.LectorDTO
import persistence.dto.LlibreDTO
import persistence.dto.PrestecDTO
import java.io.File

object BibliotecaStorage {

    private const val FILE_NAME = "biblioteca.json"

    fun desarJSON(biblioteca: Biblioteca) {
        val llibresDTO = biblioteca.cataleg.map { llibre ->
            LlibreDTO(llibre.id, llibre.titol, llibre.autor)
        }

        val lectorsDTO = biblioteca.lectors.map { lector ->
            LectorDTO(lector.id, lector.nom)
        }

        val prestecsDTO = mutableListOf<PrestecDTO>()
        biblioteca.lectors.forEach { lector ->
            lector.getLlibresPrestats().forEach { llibre ->
                prestecsDTO.add(PrestecDTO(lector.id, llibre.id))
            }
        }

        val bibliotecaDTO = BibliotecaDTO(llibresDTO, lectorsDTO, prestecsDTO)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(bibliotecaDTO)

        File(FILE_NAME).writeText(json)
    }

    fun carregarJSON(): Biblioteca {
        val file = File(FILE_NAME)
        if (!file.exists() || file.readText().isBlank()) {
            return Biblioteca()
        }

        return try {
            val json = file.readText()
            val gson = GsonBuilder().create()
            val bibliotecaDTO = gson.fromJson(json, BibliotecaDTO::class.java)

            val biblioteca = Biblioteca()

            bibliotecaDTO.llibres.forEach { llibreDTO ->
                val llibre = Llibre(llibreDTO.id, llibreDTO.titol, llibreDTO.autor)
                biblioteca.afegirLlibre(llibre)
            }

            bibliotecaDTO.lectors.forEach { lectorDTO ->
                val lector = Lector(lectorDTO.id, lectorDTO.nom)
                biblioteca.registrarLector(lector)
            }

            bibliotecaDTO.prestecs.forEach { prestecDTO ->
                val lector = biblioteca.lectors.find { it.id == prestecDTO.lectorId }
                val llibre = biblioteca.cataleg.find { it.id == prestecDTO.llibreId }

                if (lector != null && llibre != null) {
                    lector.prestarLlibre(llibre)
                }
            }
            biblioteca
        } catch (e: Exception) {
            println("Error al carregar el fitxer JSON: ${e.message}")
            Biblioteca()
        }
    }
}