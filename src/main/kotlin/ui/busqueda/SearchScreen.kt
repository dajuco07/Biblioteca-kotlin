package ui.busqueda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Llibre
import ui.LibraryState

@Composable
fun SearchScreen(state: LibraryState, onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Todos los Libros", "Buscar por Autor", "Préstamos Activos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
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
                0 -> AllBooksView(state)
                1 -> SearchByAuthorView(state)
                2 -> AllLoansView(state)
            }
        }
    }
}

@Composable
fun AllBooksView(state: LibraryState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = state.biblioteca.cataleg, key = { llibre -> "all-${llibre.id}" }) { llibre ->
            Card(modifier = Modifier.fillMaxWidth(), elevation = 2.dp) {
                Text(
                    text = "• ${llibre.titol} por ${llibre.autor}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SearchByAuthorView(state: LibraryState) {
    var autorInput by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Llibre>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = autorInput,
            onValueChange = { autorInput = it },
            label = { Text("Nombre del Autor") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { searchResults = state.cercarPerAutor(autorInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }
        Divider()
        when {
            searchResults == null -> Text("Introduce un autor para empezar a buscar.")
            searchResults!!.isEmpty() -> Text("No se han encontrado libros para '${autorInput}'.")
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = searchResults!!, key = { llibre -> "search-${llibre.id}" }) { llibre ->
                        Card(modifier = Modifier.fillMaxWidth(), elevation = 2.dp) {
                            Text(
                                text = "• ${llibre.titol}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllLoansView(state: LibraryState) {
    val hayPrestamos = state.biblioteca.lectors.any { it.getLlibresPrestats().isNotEmpty() }

    if (!hayPrestamos) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay préstamos activos en este momento.")
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        state.biblioteca.lectors.forEach { lector ->
            val prestecsDelLector = lector.getLlibresPrestats()
            if (prestecsDelLector.isNotEmpty()) {
                item {
                    Text(
                        text = "Libros prestados a: ${lector.nom}",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(items = prestecsDelLector, key = { llibre -> "loan-${lector.id}-${llibre.id}" }) { llibre ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "  - ${llibre.titol}",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
