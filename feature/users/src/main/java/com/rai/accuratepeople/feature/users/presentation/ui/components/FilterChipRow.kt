package com.rai.accuratepeople.feature.users.presentation.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rai.accuratepeople.feature.users.domain.model.City

@Composable
fun FilterChipRow(
    cities: List<City>,
    selectedCity: String,
    onCitySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        FilterChip(
            selected = selectedCity.isEmpty(),
            onClick  = { onCitySelected("") },
            label    = { Text("Semua") }
        )
        cities.forEach { city ->
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = selectedCity == city.name,
                onClick  = { onCitySelected(city.name) },
                label    = { Text(city.name) }
            )
        }
    }
}
