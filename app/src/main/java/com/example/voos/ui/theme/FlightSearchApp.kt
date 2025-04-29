package com.example.voos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voos.data.FlightSearchViewModel

@Composable
fun FlightSearchApp(viewModel: FlightSearchViewModel = hiltViewModel()) {
    val searchText by viewModel.searchText.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
    val favoriteRoutes by viewModel.favoriteRoutes.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChanged,
            label = { Text("Enter Airport Name or IATA Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (searchText.isNotEmpty()) {
            if (searchResults.isNotEmpty()) {
                Text("Resultados da Busca:", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(searchResults) { airport ->
                        AirportSuggestionItem(
                            airport = airport,
                            onAirportSelected = viewModel::onAirportSelected
                        )
                        Divider()
                    }
                }
            } else {
                Text("Nenhum aeroporto encontrado.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            if (favoriteRoutes.isNotEmpty()) {
                Text("Rotas Favoritas:", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(favoriteRoutes) { favorite ->
                        FavoriteRouteItem(
                            favorite = favorite,
                            viewModel = viewModel // Pass the ViewModel to fetch airport names
                        )
                        Divider()
                    }
                }
            } else {
                Text("Nenhuma rota favorita salva ainda.", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.selectedDepartureAirport.value != null) {
            val selectedAirport = viewModel.selectedDepartureAirport.value!!
            val availableFlights by viewModel.availableFlights.collectAsState(initial = emptyList())

            Text(
                "Voos de ${selectedAirport.iataCode} - ${selectedAirport.name}:",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(availableFlights) { destinationAirport ->
                    FlightItem(
                        departureAirport = selectedAirport,
                        destinationAirport = destinationAirport,
                        onFavoriteClick = {
                            viewModel.addFavorite(selectedAirport.iataCode, destinationAirport.iataCode)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun AirportSuggestionItem(airport: Airport, onAirportSelected: (Airport) -> Unit) {
    Button(onClick = { onAirportSelected(airport) }, modifier = Modifier.fillMaxWidth()) {
        Text("${airport.iataCode} - ${airport.name}")
    }
}

@Composable
fun FavoriteRouteItem(favorite: Favorite, viewModel: FlightSearchViewModel) {
    val departureAirport by viewModel.getAirportByCode(favorite.departureCode).collectAsState(initial = null)
    val destinationAirport by viewModel.getAirportByCode(favorite.destinationCode).collectAsState(initial = null)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "${departureAirport?.iataCode ?: "..."} - ${departureAirport?.name ?: "..."} -> " +
                    "${destinationAirport?.iataCode ?: "..."} - ${destinationAirport?.name ?: "..."}",
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = { viewModel.removeFavorite(favorite) }) {
            Text("Remover")
        }
    }
}

@Composable
fun FlightItem(departureAirport: Airport, destinationAirport: Airport, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                "De: ${departureAirport.iataCode} - ${departureAirport.name}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Para: ${destinationAirport.iataCode} - ${destinationAirport.name}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(onClick = onFavoriteClick) {
            Text("Favoritar")
        }
    }
}