
package com.example.sumativa2meap.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResumenPrestamo(
    cuotaMensual: String,
    interesTotal: String,
    totalPagar: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Resultados")

        Text("Cuota mensual: $cuotaMensual")
        Text("Interés total: $interesTotal")
        Text("Total a pagar: $totalPagar")
    }
}

