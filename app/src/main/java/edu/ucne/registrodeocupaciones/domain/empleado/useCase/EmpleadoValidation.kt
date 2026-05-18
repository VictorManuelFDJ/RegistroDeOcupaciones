package edu.ucne.registrodeocupaciones.domain.empleado.useCase

data class EmpleadoValidation(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(nombre: String, nombresExistentes: List<String> = emptyList()):
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
        nombresExistentes.any { it.equals(nombre, ignoreCase = true) } -> EmpleadoValidation(false,
            "Ya existe un empleado con este nombre")
        else -> EmpleadoValidation(true)
    }
}
val opcionesValidas = listOf("Masculino", "Femenino", "Otros")
fun validateSexo(sexo: String): EmpleadoValidation{
    val sexo = sexo.trim()
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

fun validateSueldoE(sueldo: String): EmpleadoValidation{
    return  when{
        sueldo.isBlank() -> EmpleadoValidation(false,
            "El sueldo no puede estar vacio tu no cobra es")
        sueldo.toDoubleOrNull() == null -> EmpleadoValidation(false,
            "Ingrese un sueldo valido")
        sueldo.toDouble() <= 0.0 -> EmpleadoValidation(false,
            "El sueldo tiene que ser mayor que cero")
        else -> EmpleadoValidation(true)
    }
}
