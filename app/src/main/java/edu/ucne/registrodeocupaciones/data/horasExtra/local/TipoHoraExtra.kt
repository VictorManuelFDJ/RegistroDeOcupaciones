package edu.ucne.registrodeocupaciones.data.horasExtra.local

enum class TipoHoraExtra( val mensaje: String,val factor: Double) {
    DIURNA("Diurna",1.35),
    NOCTURNA("Nocturna",1.50),
    ALTA_VOLUMEN("Alto volumen",2.0),
    FERIADO("Dias Feriados",2.0)
}