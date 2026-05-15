package edu.ucne.registrodeocupaciones.presentation.ocupacion.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormOcupacionScreen(
    viewModel: FormOcupacionViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if(state.saved){
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.ocupacionId == null || state.ocupacionId == 0) "Nueva Ocupación" else "Editar Ocupación") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { viewModel.onEvent(FormOcupacionUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_description"),
                isError = state.descripcionError != null,
                supportingText = state.descripcionError?.let { { Text(it) } },
                singleLine = false,
                minLines = 3,
                maxLines = 5,
            )
            OutlinedTextField(
                value = state.sueldo,
                onValueChange = { viewModel.onEvent(FormOcupacionUiEvent.SueldoChanged(it)) },
                label = { Text("Sueldo (DOP)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_sueldo"),
                isError = state.sueldoError != null,
                supportingText = state.sueldoError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Button(
                onClick = { viewModel.onEvent(FormOcupacionUiEvent.save) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_save"),
                enabled = !state.isSaving
            ){
                if(state.isSaving){
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}
