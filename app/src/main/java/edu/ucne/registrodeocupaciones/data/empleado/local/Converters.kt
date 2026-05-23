package edu.ucne.registrodeocupaciones.data.empleado.local

import androidx.room.TypeConverter
import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra
import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago

import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromString(value: String?): LocalDate?{
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun datatoString(date: LocalDate?): String?{
        return date?.toString()
    }

    @TypeConverter
    fun fromFrecuenciaPago(value: FrecuenciaDePago): String{
        return value.name
    }

    @TypeConverter
    fun toFrecuenciaPago(value: String): FrecuenciaDePago{
        return FrecuenciaDePago.valueOf(value)
    }

    @TypeConverter
    fun fromTipoHoraExtra(value: TipoHoraExtra): String{
        return value.name
    }

    @TypeConverter
    fun toTipoHoraExtra(value: String): TipoHoraExtra {
        return TipoHoraExtra.valueOf(value)
    }
}