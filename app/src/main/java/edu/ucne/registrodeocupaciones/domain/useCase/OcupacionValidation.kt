package edu.ucne.registrodeocupaciones.domain.useCase

data class OcupacionValidation(
    val isValid: Boolean,
    val error: String? = null
)

fun validateDescription(descripcion: String, ocupacioneExistentes: List<String>):
        OcupacionValidation{
    return when{
        descripcion.isBlank() -> OcupacionValidation(false,
            "Llena lo que esta aqui bro que esto no puede estar vacio")
        descripcion.length <3 -> OcupacionValidation(false,
            "Tiene que tener mas de 3 caracteres para que sea mas grande guiño")
        ocupacioneExistentes.any{it.equals(descripcion.trim(), ignoreCase = true)}
             -> OcupacionValidation(false,
            "La descripcion esta duplicada por favor ingresar otra descripcion")
        else -> OcupacionValidation(true)
    }
}

fun validateSueldo(sueldo: String): OcupacionValidation{
    return  when{
        sueldo.isBlank() -> OcupacionValidation(false,
            "El sueldo no puede estar vacio tu no cobra es")
        sueldo.toDoubleOrNull() == null -> OcupacionValidation(false,
            "Ingrese un sueldo valido")
        sueldo.toDouble() <= 0.0 -> OcupacionValidation(false,
            "El sueldo tiene que ser mayor que cero")
        else -> OcupacionValidation(true)
    }
}