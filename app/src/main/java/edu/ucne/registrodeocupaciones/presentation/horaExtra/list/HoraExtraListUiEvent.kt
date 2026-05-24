package edu.ucne.registrodeocupaciones.presentation.horaExtra.list

import edu.ucne.registrodeocupaciones.presentation.empleado.list.EmpleadoListUiEvent

sealed class HoraExtraListUiEvent{
    object Load : HoraExtraListUiEvent()

    object Refresh : HoraExtraListUiEvent()

    data class Delete(val id: Int) : HoraExtraListUiEvent()

    data class ShowMessage(val message: String) : HoraExtraListUiEvent()

    object ClearMessage : HoraExtraListUiEvent()

    object CreateNew : HoraExtraListUiEvent()

    data class Edit(val id: Int) : HoraExtraListUiEvent()
}