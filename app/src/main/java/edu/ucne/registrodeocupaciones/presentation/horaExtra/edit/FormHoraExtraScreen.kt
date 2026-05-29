package edu.ucne.registrodeocupaciones.presentation.horaExtra.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra
import edu.ucne.registrodeocupaciones.presentation.empleado.edit.FormEmpleadoUiEvent
import edu.ucne.registrodeocupaciones.presentation.ocupacion.edit.FormOcupacionUiEvent
import java.time.Instant
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormHoraExtraScreen(
    horaExtraId: Int,
    viewModel: FormHoraExtraViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    var expandedEmpleado by remember { mutableStateOf(false) }
    var expandedTipoHora by remember { mutableStateOf(false) }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(key1 = horaExtraId) {
        viewModel.loadHoraExtra(horaExtraId)
    }

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted){
            onBack()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew)"Registrar Horas Extras" else "Editar Horas Extras",
                    style = MaterialTheme.typography.titleMedium) },
                windowInsets = WindowInsets(0.dp),
                navigationIcon = {
                    IconButton(onClick = onBack){
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atras")
                    }
                },
                actions = {
                    if(!state.isNew){
                        IconButton(
                            onClick = {viewModel.onEvent(FormHoraExtraUiEvent.Delete)},
                            modifier = Modifier.testTag("btn_delete")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar Ocupacion",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )

        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            ExposedDropdownMenuBox(
                expanded = expandedEmpleado,
                onExpandedChange = { expandedEmpleado = it }
            ){
                OutlinedTextField(
                    value = state.empleadoDisponibles
                        .find { it.empleadoId == state.empleadoId }?. nombre?:
                        "Seleccione un empleado",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Empleado")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmpleado)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable )
                        .testTag("input_empleado"),
                    isError = state.empleadoError != null,
                    supportingText = state.empleadoError?.let { {Text(it)} }
                )
                ExposedDropdownMenu(
                    expanded = expandedEmpleado,
                    onDismissRequest = {expandedEmpleado = false}
                ) {
                    state.empleadoDisponibles.forEach { empleado ->
                        DropdownMenuItem(
                            text = {Text(empleado.nombre)},
                            onClick = {
                                viewModel.onEvent(FormHoraExtraUiEvent.EmpleadoIdChange(empleado.empleadoId))
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.fecha.toString(),
                onValueChange = { },
                label = {Text("Fecha de Ingreso")},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_fecha"),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {showDatePicker = true}) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Selecionar fecha"
                        )
                    }
                },
                isError = state.fechaError != null,
                supportingText = state.fechaError?.let { { Text(it) } },
                singleLine = true
            )

            if(showDatePicker){
                DatePickerDialog(
                    onDismissRequest = {showDatePicker = false},
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                viewModel.onEvent(FormHoraExtraUiEvent.FechaChange(date))
                            }
                            showDatePicker = false
                        }) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {showDatePicker = false}) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            OutlinedTextField(
                value = state.cantidadDeHora,
                onValueChange = {viewModel.onEvent(FormHoraExtraUiEvent.CantidadHoraChange(it))},
                label = { Text("Cantidad de Horas Extras") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_cantidad_horas"),
                isError = state.cantidadDeHoraError != null,
                supportingText = state.cantidadDeHoraError?.let { {Text(it)} },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = expandedTipoHora,
                onExpandedChange = { expandedTipoHora = it }
            ) {
                OutlinedTextField(
                    value = state.tipoHoraExtra?.name ?: "Seleccione tipo",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Recargo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoHora) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .testTag("input_tipo_hora"),
                    isError = state.tipoHoraExtraError != null,
                    supportingText = state.tipoHoraExtraError?.let { { Text(it) } },
                )
                ExposedDropdownMenu(
                    expanded = expandedTipoHora,
                    onDismissRequest = {expandedTipoHora = false}
                ) {
                    TipoHoraExtra.entries.forEach { tipoHoraExtra ->
                        DropdownMenuItem(
                            text = {Text(tipoHoraExtra.name)},
                            onClick = {
                                viewModel.onEvent(FormHoraExtraUiEvent.TipoHoraExtraChange(tipoHoraExtra))
                                expandedTipoHora = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {viewModel.onEvent(FormHoraExtraUiEvent.Save)},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_save"),
                enabled = !state.isSaving
            ) {
                if (state.isSaving){
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }else{
                    Text("Guardar")
                }
            }


        }

    }



}