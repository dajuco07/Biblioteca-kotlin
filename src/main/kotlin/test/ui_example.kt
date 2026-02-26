import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/* ===========================
   MODEL
   =========================== */

data class Mascota(
    val id: Int,
    val nom: String,
    val especie: String
)

/* ===========================
   ESTAT
   =========================== */

class VetState {

    private var contadorId = 1

    var mascotes = mutableStateListOf<Mascota>()
        private set

    fun afegir(nom: String, especie: String) {

        if (nom.isBlank() || especie.isBlank()) return

        mascotes.add(
            Mascota(
                id = contadorId++,
                nom = nom,
                especie = especie
            )
        )
    }
}

/* ===========================
   APLICACIÓ PRINCIPAL
   =========================== */

fun main() = application {

    val state = remember { VetState() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Clínica Veterinària"
    ) {
        MaterialTheme {
            VetApp(state)
        }
    }
}

/* ===========================
   INTERFÍCIE
   =========================== */

@Composable
fun VetApp(state: VetState) {

    var nom by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Clínica Veterinària",
            style = MaterialTheme.typography.h4
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = especie,
            onValueChange = { especie = it },
            label = { Text("Espècie") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {

                state.afegir(nom, especie)

                nom = ""
                especie = ""
            }
        ) {
            Text("Afegir Mascota")
        }

        Spacer(Modifier.height(24.dp))

        Divider()

        Spacer(Modifier.height(12.dp))

        Text(
            "Llistat de Mascotes",
            style = MaterialTheme.typography.h6
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(state.mascotes) { mascota ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    elevation = 4.dp
                ) {

                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(mascota.nom)

                        Text(mascota.especie)
                    }
                }
            }
        }
    }
}