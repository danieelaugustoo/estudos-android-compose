package com.example.voos.data

import androidx.lifecycle.*
import com.example.voos.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText
        .debounce(300)
        .onEach { userDataRepository.saveSearchText(it) }
        .flatMapLatest { query ->
            if (query.isNotEmpty()) {
                airportDao.findAirports(query)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchResults = MutableStateFlow<List<com.example.voos.Airport>>(emptyList())
    val searchResults: StateFlow<List<com.example.voos.Airport>> = _searchResults

    private val _favoriteRoutes = favoriteDao.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val favoriteRoutes: StateFlow<List<Favorite>> = _favoriteRoutes

    private val _selectedDepartureAirport = MutableStateFlow<com.example.voos.Airport?>(null)
    val selectedDepartureAirport: StateFlow<com.example.voos.Airport?> = _selectedDepartureAirport

    val availableFlights: StateFlow<List<com.example.voos.Airport>> = _selectedDepartureAirport
        .filterNotNull()
        .flatMapLatest { departureAirport ->
            airportDao.getAllAirportsExcept(departureAirport.iataCode)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            userDataRepository.searchText.collect { savedText ->
                _searchText.value = savedText
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onAirportSelected(airport: com.example.voos.Airport) {
        _selectedDepartureAirport.value = airport
    }

    fun addFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.insertFavorite(Favorite(departureCode = departureCode, destinationCode = destinationCode))
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.deleteFavorite(favorite)
        }
    }

    fun getAirportByCode(iataCode: String): Flow<com.example.voos.Airport?> = airportDao.getAirportByCode(iataCode)
}