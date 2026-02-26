package ui.prestec

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.LibraryState

@Composable
fun LoanScreen(state: LibraryState, onBack: () -> Unit) {
    var lectorIdInput by remember { mutableStateOf("") }
    var llibreIdInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Realizar Préstamo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Libros Disponibles", style = MaterialTheme.typography.h6)
                LazyColumn {
                    items(state.biblioteca.cataleg.filter { it.disponible }) { llibre ->
                        Text("• ${llibre.titol} (ID: ${llibre.id})")
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Lectores Registrados", style = MaterialTheme.typography.h6)
                LazyColumn {
                    items(state.biblioteca.lectors) { lector ->
                        Text("• ${lector.nom} (ID: ${lector.id})")
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = llibreIdInput,
                    onValueChange = { llibreIdInput = it },
                    label = { Text("ID del Libro a Prestar") }
                )
                OutlinedTextField(
                    value = lectorIdInput,
                    onValueChange = { lectorIdInput = it },
                    label = { Text("ID del Lector") }
                )
                Button(onClick = {
                    message = state.prestarLlibre(lectorIdInput, llibreIdInput)
                    if (message.startsWith("¡Éxito!")) {
                        lectorIdInput = ""
                        llibreIdInput = ""
                    }
                }) {
                    Text("Realizar Préstamo")
                }
                if (message.isNotBlank()) {
                    val color = if (message.startsWith("Error:")) Color.Red else Color.Green
                    Text(message, color = color)
                }
            }
        }
    }
}
