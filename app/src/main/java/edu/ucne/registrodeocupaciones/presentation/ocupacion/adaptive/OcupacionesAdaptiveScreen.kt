package edu.ucne.registrodeocupaciones.presentation.ocupacion.adaptive

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import edu.ucne.registrodeocupaciones.presentation.horaExtra.edit.FormHoraExtraScreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.list.HoraExtraListScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.edit.FormOcupacionScreen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.list.OcupacionListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun OcupacionesAdaptiveScreen(){
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()
    var selectedOcupacionId by remember { mutableStateOf(0)}

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                OcupacionListScreen(
                onAddOcupacion = {
                    selectedOcupacionId = 0
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                },
                onEditOcupacion = { id ->
                    selectedOcupacionId = id
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                }
            )
            }
        },
        detailPane = {
            AnimatedPane {
                FormOcupacionScreen(
                    ocupacionId = selectedOcupacionId,
                    onBack = {
                        scope.launch {
                            navigator.navigateBack()
                        }
                    }
                )
            }

        }
    )

}
