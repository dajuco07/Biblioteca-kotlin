package ui.registrar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.LibraryState

@Composable
fun RegisterScreen(state: LibraryState, onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Añadir Libro", "Añadir Lector")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") } }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTab) {
                0 -> AddBookView(state)
                1 -> AddReaderView(state)
            }
        }
    }
}

@Composable
fun AddBookView(state: LibraryState) {
    var id by remember { mutableStateOf("") }
    var titol by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Añadir Nuevo Libro", style = MaterialTheme.typography.h6)
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID del Libro") })
        OutlinedTextField(value = titol, onValueChange = { titol = it }, label = { Text("Título") })
        OutlinedTextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") })

        Button(onClick = {
            if (state.afegirLlibre(id, titol, autor)) {
                id = ""; titol = ""; autor = ""
            }
        }) {
            Text("Guardar Libro")
        }

        Text("Libros en la Biblioteca:", style = MaterialTheme.typography.h6, modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(state.biblioteca.cataleg) { llibre ->
                Text("• ${llibre.titol}")
            }
        }
    }
}

@Composable
fun AddReaderView(state: LibraryState) {
    var id by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Añadir Nuevo Lector", style = MaterialTheme.typography.h6)
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID del Lector") })
        OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nombre") })

        Button(onClick = {
            if (state.registrarLector(id, nom)) {
                id = ""; nom = ""
            }
        }) {
            Text("Guardar Lector")
        }

        Text("Lectores Registrados:", style = MaterialTheme.typography.h6, modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(state.biblioteca.lectors) { lector ->
                Text("• ${lector.nom}")
            }
        }
    }
}
