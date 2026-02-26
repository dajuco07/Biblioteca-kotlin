package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val libraryState = remember { LibraryState() }

    Window(
        onCloseRequest = {
            libraryState.guardarDades()
            exitApplication()
        },
        title = "Biblioteca App"
    ) {
        MaterialTheme {
            MainApp()
        }
    }
}
