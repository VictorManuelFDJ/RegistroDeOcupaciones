package edu.ucne.registrodeocupaciones.presentation.ocupacion.list

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import  edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion

@Composable
fun OcupacionListScreen(
    viewModel: OcupacionListViewModel = hiltViewModel(),
    onAddOcupacion: () -> Unit,
    onEditOcupacion: (Int) -> Unit
){
 val state by viewModel.state.collectAsStateWithLifecycle()
 OcupacionListBody(state, viewModel:: onEvent, onAddOcupacion, onEditOcupacion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionListBody(
    state: OcupacionListUiState,
    onEvent: (OcupacionListUiEvent) -> Unit,
    onAddOcupacion: () -> Unit,
    onEditOcupacion: (Int) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let { message -> snackbarHostState.showSnackbar(message)
        onEvent(OcupacionListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddOcupacion,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }
    ){padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()
        ){
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).testTag("loading")
                )
            }else{
                if(state.ocupacion.isEmpty()){
                    Text(
                        text = "No hay Ocupaciones",
                        modifier = Modifier.align(Alignment.Center).
                        testTag("entity_Message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }else{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.ocupacion,
                            key = {it.ocupacionId}
                        ){ ocupacion -> 
                            OcupacionItems(
                                ocupacion = ocupacion,
                                onEdit = { onEditOcupacion(ocupacion.ocupacionId) },
                                onDelete = { onEvent(OcupacionListUiEvent.Delete(ocupacion.ocupacionId)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun OcupacionItems(
    ocupacion: Ocupacion,
    onEdit: () -> Unit,
    onDelete: () -> Unit
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ocupacion_item_${ocupacion.ocupacionId}"),
        onClick = onEdit
    ) {
        Row(modifier = Modifier.
        fillMaxWidth().
        padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ocupacion.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${ocupacion.sueldo} DOP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${ocupacion.ocupacionId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Ocupacion"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OcupacionListBodyPreview(){
    MaterialTheme{
        val state = OcupacionListUiState(
            isLoading = false,
            ocupacion = listOf(
                Ocupacion(ocupacionId = 1, descripcion = "Esto es una prueba", sueldo = 200.0)
            )
        )
        OcupacionListBody(state, {}, {}, {})
    }
}
