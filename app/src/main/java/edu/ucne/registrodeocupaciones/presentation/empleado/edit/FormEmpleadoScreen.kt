package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.opcionesValidas
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEmpleadoScreen(
    viewModel: FormEmpleadoViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false)}
    val datePickerState = rememberDatePickerState()

    var expandedSexo by remember { mutableStateOf(false) }
    var expandedFrecuencia by remember { mutableStateOf(false) }
    var expandedOcupacion by remember { mutableStateOf(false) }


    LaunchedEffect(state.saved) {
        if (state.saved){
            onBack()
        }
    }
    Scaffold(
        topBar = {
        TopAppBar(
            title = { Text(if (state.isNew)"Nuevo Empleado" else "Editar Empleado") },
            navigationIcon = { IconButton(onClick = onBack){
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atras")
            }
            }
        )
    }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            OutlinedTextField(
                value = state.nombre,
                onValueChange = {viewModel.onEvent(FormEmpleadoUiEvent.NombreChanged(it))},
                label = {Text("Nombre")},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_nombre"),
                isError = state.nombreError != null,
                supportingText = state.nombreError?.let { {Text(it)} },
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = expandedSexo,
                onExpandedChange = {expandedSexo = it}

            ) {
                OutlinedTextField(
                    value = state.sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sexo")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSexo)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .testTag("input_sexo"),
                    isError = state.sexoError != null,
                    supportingText = state.sexoError?.let { { Text(it) } }
                )
                ExposedDropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = {expandedSexo = false}
                ) {
                    opcionesValidas.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                viewModel.onEvent(FormEmpleadoUiEvent.SexoChanged(opcion))
                                expandedSexo = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.fechaIngreso.toString(),
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
            ExposedDropdownMenuBox(
                expanded = expandedFrecuencia,
                onExpandedChange = {expandedFrecuencia = it}
            ) {
                OutlinedTextField(
                    value = state.frecuenciaDePago.mensaje,
                    onValueChange = {},
                    readOnly = true,
                    label = {Text("Frecuencia de Pago")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFrecuencia)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .testTag("input_frecuencia"),
                    isError = state.frecuenciaDePagoError != null,
                    supportingText = state.frecuenciaDePagoError?.let{ { Text(it) } },
                )
                ExposedDropdownMenu(
                    expanded = expandedFrecuencia,
                    onDismissRequest = {expandedFrecuencia = false}
                ) {
                    FrecuenciaDePago.entries.forEach { frecuencia ->
                        DropdownMenuItem(
                            text = {Text(frecuencia.mensaje)},
                            onClick = {
                                viewModel.onEvent(FormEmpleadoUiEvent.FrecuenciaDePagoChanged(frecuencia))
                                expandedFrecuencia = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedOcupacion,
                onExpandedChange = {expandedOcupacion = it}
            ) {
                OutlinedTextField(
                    value = state.ocupacionDisponible
                        .find { it.ocupacionId == state.ocupacionId}
                        ?.descripcion?: "Selecione ocupacion",
                    onValueChange = {},
                    readOnly = true,
                    label = {Text("Ocupacion ")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOcupacion)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .testTag("input_Ocupacion"),
                    isError = state.ocupacionError != null,
                    supportingText = state.ocupacionError?.let { {Text(it) } },
                )
                ExposedDropdownMenu(
                    expanded = expandedOcupacion,
                    onDismissRequest = {expandedOcupacion = false}
                ) {
                    state.ocupacionDisponible.forEach { ocupacion ->
                        DropdownMenuItem(
                            text = {Text(ocupacion.descripcion)},
                            onClick = {
                                viewModel.onEvent(FormEmpleadoUiEvent.OcupacionIdChanged(ocupacion.ocupacionId))
                                expandedOcupacion = false
                            }
                        )
                    }
                }
            }

            if(showDatePicker){
                DatePickerDialog(
                    onDismissRequest = {showDatePicker = false},
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                viewModel.onEvent(FormEmpleadoUiEvent.FechaIngresoChanged(date))
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
                value = state.sueldo,
                onValueChange = {viewModel.onEvent(FormEmpleadoUiEvent.SueldoChanged(it))},
                label = {Text("Sueldo(DOP)")},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_sueldo"),
                isError = state.sueldoError != null,
                supportingText = state.sueldoError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Button(
                onClick = {viewModel.onEvent(FormEmpleadoUiEvent.Save)},
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
