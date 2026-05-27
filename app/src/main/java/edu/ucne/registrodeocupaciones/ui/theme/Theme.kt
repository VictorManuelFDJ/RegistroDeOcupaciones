package edu.ucne.registrodeocupaciones.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class TemaApp {
    CORPORATIVO, ESMERALDA, OSCURO_ELEGANTE
}
private val TemaCorporativo = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),
    background = Color(0xFFF4F6F8),
    surface = Color.White,
    onSurface = Color(0xFF1D1D1D)
)

private val TemaEsmeralda = lightColorScheme(
    primary = Color(0xFF00695C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2DFDB),
    background = Color(0xFFF2F7F6),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A)
)
private val TemaOscuroElegante = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1565C0),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color.White,
    onSurface = Color.White
)


@Composable
fun RegistroDeOcupacionesTheme(
    temaSeleccionado: TemaApp = TemaApp.OSCURO_ELEGANTE,
    content: @Composable () -> Unit
) {
    val colorScheme = when (temaSeleccionado) {
        TemaApp.CORPORATIVO -> TemaCorporativo
        TemaApp.ESMERALDA -> TemaEsmeralda
        TemaApp.OSCURO_ELEGANTE -> TemaOscuroElegante
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}