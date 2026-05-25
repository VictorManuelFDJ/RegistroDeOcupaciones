package edu.ucne.registrodeocupaciones.presentation.horaExtra.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import org.w3c.dom.Text
import java.time.format.DateTimeFormatter

@Composable
fun HoraExtraListScreen(
    viewModel: HoraExtraListViewModel = hiltViewModel(),
    onAddHoraExtra: () -> Unit,
    onEditHoraExtra: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    HoraExtraListBody(state, viewModel:: onEvent, onAddHoraExtra, onEditHoraExtra)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoraExtraListBody(
    state: HoraExtraListUiState,
    onEvent: (HoraExtraListUiEvent) -> Unit,
    onAddHoraExtra: () -> Unit,
    onEditHoraExtra: (Int) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
    state.message?.let { message ->
        snackbarHostState.showSnackbar(message)
        onEvent(HoraExtraListUiEvent.ClearMessage)}
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Horas Extras")
                },
                modifier = Modifier.height(48.dp),
                windowInsets = WindowInsets(0.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHoraExtra,
                modifier = Modifier.testTag("fab_add_hora_Extra")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Calcular horas extra"
                )
            }
        }

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            if (state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")

                )
            }else{
                if(state.horasExtras.isEmpty()){
                    Text(
                        text = "No hay horas extras",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("entity_Message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }else{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.horasExtras,
                            key = {it.horaExtraId}
                        ){horaExtra ->
                            val empleadoEncontrado = state.empleados.find { it.empleadoId == horaExtra.empleadoId }
                            val nombreAMostrar = empleadoEncontrado?.nombre ?: "Empleado Desconocido"
                            HoraExtraItem(
                            horaExtra = horaExtra,
                            nombreEmpleado = nombreAMostrar,
                            onEdit = { onEditHoraExtra(horaExtra.horaExtraId) }
                        )}
                    }
                }
            }
        }

    }
}

@Composable
fun HoraExtraItem(
    horaExtra: HoraExtra,
    nombreEmpleado: String,
    onEdit: () -> Unit,

){
    val formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFormateada = horaExtra.fecha.format(formateador)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hora_extra_item_${horaExtra.horaExtraId}"),
        onClick = onEdit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription =  null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombreEmpleado,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${horaExtra.cantidadHoras} hora • ${fechaFormateada}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Total a pagar: RD$ ${horaExtra.recargo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}