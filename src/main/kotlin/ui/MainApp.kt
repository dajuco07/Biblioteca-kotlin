package ui

import androidx.compose.runtime.*
import ui.busqueda.SearchScreen
import ui.menu.MainMenu
import ui.navigation.Screen
import ui.prestec.LoanScreen
import ui.registrar.RegisterScreen

@Composable
fun MainApp() {
    val libraryState = remember { LibraryState() }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu) }

    when (currentScreen) {
        is Screen.MainMenu -> MainMenu(onNavigate = { newScreen -> currentScreen = newScreen })
        is Screen.Register -> RegisterScreen(
            state = libraryState,
            onBack = { currentScreen = Screen.MainMenu }
        )
        is Screen.Loan -> LoanScreen(
            state = libraryState,
            onBack = { currentScreen = Screen.MainMenu }
        )
        is Screen.Search -> SearchScreen(
            state = libraryState,
            onBack = { currentScreen = Screen.MainMenu }
        )
    }
}
