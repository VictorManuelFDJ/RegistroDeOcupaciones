package edu.ucne.registrodeocupaciones.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrodeocupaciones.presentation.empleado.edit.FormEmpleadoScreen
import edu.ucne.registrodeocupaciones.presentation.empleado.list.EmpleadoListScreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.edit.FormHoraExtraSreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.list.HoraExtraListScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.edit.FormOcupacionScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.list.OcupacionListScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList,
        modifier = Modifier.padding(innerPadding)
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

        composable<Screen.EmpleadoList> {
            EmpleadoListScreen(
                onAddEmpleado = {
                    navController.navigate(Screen.EmpleadoForm())
                },
                onEditEmpleado = { id ->
                    navController.navigate(Screen.EmpleadoForm(empleadoId = id))
                }
            )
        }

        composable<Screen.EmpleadoForm> {
            FormEmpleadoScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.HoraExtraList>{
            HoraExtraListScreen(
                onAddHoraExtra = {
                    navController.navigate(Screen.HoraExtraForm())
                },
                onEditHoraExtra = {id ->
                    navController.navigate(Screen.HoraExtraForm(horaExtraId = id))
                }
            )
        }
        composable<Screen.HoraExtraForm>{
            FormHoraExtraSreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
