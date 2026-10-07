package com.example.sumativa2meap.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sumativa2meap.components.*

@Composable
fun CalcularPrestamo(
    modifier: Modifier = Modifier
) {
    var monto by remember { mutableStateOf("") }
    var plazo by remember { mutableStateOf("") }
    var interes by remember { mutableStateOf("") }

    var cuotaMensual by remember { mutableStateOf(0.0) }
    var interesTotal by remember { mutableStateOf(0.0) }
    var totalPagar by remember { mutableStateOf(0.0) }

    var mensajeErrorMonto by remember { mutableStateOf<String?>(null) }
    var mensajeErrorPlazo by remember { mutableStateOf<String?>(null) }
    var mensajeErrorInteres by remember { mutableStateOf<String?>(null) }

    val plazosPermitidos = listOf(12, 24,36,48,60, 72, 90)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Calculadora de préstamo")

        Monto(
            valor = monto,
            alCambiar = {
                monto = it
                mensajeErrorMonto = null
            },
            error = mensajeErrorMonto
        )

        Plazo(
            valor = plazo,
            alCambiar = {
                plazo = it
                mensajeErrorPlazo = null
            },
            error = mensajeErrorPlazo
        )

        InteresAnual(
            valor = interes,
            alCambiar = {
                interes = it
                mensajeErrorInteres = null
            },
            error = mensajeErrorInteres
        )

        Button(
            onClick = {
                val montoNum = monto.toDoubleOrNull()
                val plazoNum = plazo.toIntOrNull()
                val interesNum = interes.toDoubleOrNull()

                mensajeErrorMonto = when {
                    monto.isBlank() -> "El monto es obligatorio"
                    montoNum == null -> "Ingrese un monto válido"
                    montoNum <= 0 -> "El monto debe ser mayor que 0"
                    else -> null
                }

                mensajeErrorPlazo = when {
                    plazo.isBlank() -> "El plazo es obligatorio"
                    plazoNum == null -> "Ingrese un plazo válido"
                    plazoNum !in plazosPermitidos ->
                        "El plazo debe ser 12, 24, 36, 48, 60, 72 o 90 meses"
                    else -> null
                }

                mensajeErrorInteres = when {
                    interes.isBlank() -> "El interés es obligatorio"
                    interesNum == null -> "Ingrese un interés válido"
                    interesNum < 3 || interesNum > 16 ->
                        "El interés debe estar entre 3% y 16%"
                    else -> null
                }

                if (
                    montoNum != null &&
                    montoNum > 0 &&
                    plazoNum != null &&
                    plazoNum in plazosPermitidos &&
                    interesNum != null &&
                    interesNum in 3.0..16.0
                ) {
                    val tasaMensual = interesNum / 100.0 / 12.0

                    cuotaMensual = if (tasaMensual == 0.0) {
                        montoNum / plazoNum
                    } else {
                        montoNum *
                                (tasaMensual * Math.pow( 1 + tasaMensual, plazoNum.toDouble()))
                        /
                                (
                                        Math.pow(
                                            1 + tasaMensual,
                                            plazoNum.toDouble()
                                        ) - 1
                                        )
                    }

                    totalPagar = cuotaMensual * plazoNum
                    interesTotal = totalPagar - montoNum
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(8.dp))

        ResumenPrestamo(
            cuotaMensual = String.format("$%.2f", cuotaMensual),
            interesTotal = String.format("$%.2f", interesTotal),
            totalPagar = String.format("$%.2f", totalPagar)
        )
    }
}