package ui.navigation

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object Register : Screen("register")
    object Loan : Screen("loan")
    object Search : Screen("search")
}
