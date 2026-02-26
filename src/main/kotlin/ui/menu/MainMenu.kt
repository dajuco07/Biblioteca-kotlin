package ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.navigation.Screen

@Composable
fun MainMenu(onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Gestión de Biblioteca", style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(32.dp))

        Button(onClick = { onNavigate(Screen.Register) }) {
            Text("Registrar")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = { onNavigate(Screen.Loan) }) {
            Text("Préstamos")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = { onNavigate(Screen.Search) }) {
            Text("Consultas")
        }
    }
}
