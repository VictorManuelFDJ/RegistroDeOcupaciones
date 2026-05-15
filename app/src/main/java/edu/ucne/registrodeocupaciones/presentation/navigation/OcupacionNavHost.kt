package edu.ucne.registrodeocupaciones.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registrodeocupaciones.presentation.ocupacion.edit.FormOcupacionScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.list.OcupacionListScreen

@Composable
fun OcupacionNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList
    ) {
        composable<Screen.OcupacionList> {
            OcupacionListScreen(
                onAddOcupacion = {
                    navController.navigate(Screen.OcupacionForm())
                },
                onEditOcupacion = { id ->
                    navController.navigate(Screen.OcupacionForm(ocupacionId = id))
                }
            )
        }

        composable<Screen.OcupacionForm> {
            FormOcupacionScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
