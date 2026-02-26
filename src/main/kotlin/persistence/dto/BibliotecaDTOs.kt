package persistence.dto

data class LlibreDTO(
    val id: String,
    val titol: String,
    val autor: String
)

data class LectorDTO(
    val id: String,
    val nom: String
)

data class PrestecDTO(
    val lectorId: String,
    val llibreId: String
)

data class BibliotecaDTO(
    val llibres: List<LlibreDTO>,
    val lectors: List<LectorDTO>,
    val prestecs: List<PrestecDTO>
)
