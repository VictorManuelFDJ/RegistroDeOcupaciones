package edu.ucne.registrodeocupaciones.presentation.empleado.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import java.time.format.DateTimeFormatter


@Composable
fun EmpleadoListScreen(
    viewModel: EmpleadoListViewModel = hiltViewModel(),
    onAddEmpleado: () -> Unit,
    onEditEmpleado: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    EmpleadoListBody(state, viewModel::onEvent, onAddEmpleado, onEditEmpleado)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoListBody(
    state: EmpleadoListUiState,
    onEvent: (EmpleadoListUiEvent) -> Unit,
    onAddEmpleado: () -> Unit,
    onEditEmpleado: (Int) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(EmpleadoListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        topBar = {
            Row(verticalAlignment =  Alignment.CenterVertically){
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                 Text("Empleados")
            }

        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEmpleado,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Empleado"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else {
                if (state.empleado.isEmpty()) {
                    Text(
                        text = "No hay Empleados",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("entity_Message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.empleado,
                            key = { it.empleadoId }
                        ) { empleado ->
                            EmpleadoItems(
                                empleado = empleado,
                                onEdit = { onEditEmpleado(empleado.empleadoId) },
                                onDelete = { onEvent(EmpleadoListUiEvent.Delete(empleado.empleadoId)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmpleadoItems(
    empleado: Empleado,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFormateada = empleado.fechaIngreso.format(formateador)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("empleado_item_${empleado.empleadoId}"),
        onClick = onEdit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = empleado.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Sueldo: RD$ ${empleado.sueldo} • $fechaFormateada",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${empleado.empleadoId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Empleado"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmpleadoListBodyPreview() {
    MaterialTheme {
        val state = EmpleadoListUiState(
            isLoading = false,
            empleado = listOf(
                Empleado(
                    empleadoId = 1,
                    nombre = "Juan Perez",
                    sueldo = 25000.0,
                    fechaIngreso = java.time.LocalDate.now(),
                    sexo = "M"
                )
            )
        )
        EmpleadoListBody(state, {}, {}, {})
    }
}
