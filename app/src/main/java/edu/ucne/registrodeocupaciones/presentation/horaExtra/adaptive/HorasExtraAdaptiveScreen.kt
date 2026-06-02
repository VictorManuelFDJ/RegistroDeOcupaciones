package edu.ucne.registrodeocupaciones.presentation.horaExtra.adaptive

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
import androidx.navigation.Navigator
import edu.ucne.registrodeocupaciones.presentation.horaExtra.edit.FormHoraExtraScreen
import edu.ucne.registrodeocupaciones.presentation.horaExtra.list.HoraExtraListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HorasExtraAdaptiveScreen(){
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()
    var selectedHoraExtraId by remember { mutableStateOf(0)}

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                HoraExtraListScreen(
                onAddHoraExtra = {
                    selectedHoraExtraId = 0
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                },
                onEditHoraExtra = { id ->
                    selectedHoraExtraId = id
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                }
              )
            }

        },
        detailPane = {
            AnimatedPane {
                FormHoraExtraScreen(
                    horaExtraId = selectedHoraExtraId,
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