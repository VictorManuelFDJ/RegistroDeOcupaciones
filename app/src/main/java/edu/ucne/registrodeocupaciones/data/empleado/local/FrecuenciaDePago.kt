package edu.ucne.registrodeocupaciones.data.empleado.local

enum class FrecuenciaDePago(val mensaje: String,val divisor: Double){
    MENSUAL( "Mensual",23.83),
    QUINCENAL("Quincenal",11.91),
    SEMANAL("Semanal",5.5)
}