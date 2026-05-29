package edu.ucne.registrodeocupaciones.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrodeocupaciones.presentation.empleado.adaptive.EmpleadosAdaptiveScreen
import edu.ucne.registrodeocupaciones.presentation.empleado.list.EmpleadoListScreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.adaptive.HorasExtraAdaptiveScreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.list.HoraExtraListScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.adaptive.OcupacionesAdaptiveScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.list.OcupacionListScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable<Screen.OcupacionList>{
            OcupacionesAdaptiveScreen()
        }
        composable<Screen.EmpleadoList>{
            EmpleadosAdaptiveScreen()
        }
        composable<Screen.HoraExtraList>{
            HorasExtraAdaptiveScreen()
        }










    }
}
