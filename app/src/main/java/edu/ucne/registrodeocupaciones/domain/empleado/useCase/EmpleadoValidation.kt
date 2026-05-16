package edu.ucne.registrodeocupaciones.domain.empleado.useCase

data class EmpleadoValidation(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(nombre: String, empleadoExistentes: List<String>):
        EmpleadoValidation{
    return when{
        nombre.isBlank() -> EmpleadoValidation(false,
            "Este campo es obligatorio por favor llenalo")
        nombre.length <2  -> EmpleadoValidation(false,
            "El nombre tiene que ser mayor de dos letras")
        !nombre.all{it.isLetter() || it.isWhitespace() } -> EmpleadoValidation(
            false, "El nombre no puede contener números ni caracteres especiales"
        )
        nombre.contains("  ") -> EmpleadoValidation(
            false, "El nombre no puede tener espacios consecutivos"
        )
        nombre.length > 16 -> EmpleadoValidation(false,
            "El nombre es demasiado largo (máximo 16 caracteres)")
        else -> EmpleadoValidation(true)
    }
}
fun validateSexo(sexo: String): EmpleadoValidation{
    val sexo = sexo.trim()
    val opcionesValidas = listOf("Masculino", "Femenino", "Otros")
    return when{
        sexo.isBlank() -> EmpleadoValidation(false,
            "Debe seleccionar un sexo de la lista")
        opcionesValidas.none(){it.equals(sexo, ignoreCase = true)} -> EmpleadoValidation(false,
            "El sexo seleccionado no es válido")
        else -> EmpleadoValidation(true, "")
    }
}
fun validateFecha(fecha: java.time.LocalDate): EmpleadoValidation{
    val fechaActual = java.time.LocalDate.now()
    return when{
        fecha.isAfter(fechaActual) -> EmpleadoValidation(false,
            "La fecha de ingreso no puede ser una fecha futura")
        fecha.isBefore(java.time.LocalDate.of(2000,1,1)) -> EmpleadoValidation(false,
            "La fecha de ingreso no puede ser anterior al año 2000")
        else -> EmpleadoValidation(true)
    }
}